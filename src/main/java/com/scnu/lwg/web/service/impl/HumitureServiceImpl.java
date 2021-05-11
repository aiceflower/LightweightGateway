package com.scnu.lwg.web.service.impl;

import com.scnu.lwg.util.CryptoUtils;
import com.scnu.lwg.util.JSONUtils;
import com.scnu.lwg.web.entity.Humiture;
import com.scnu.lwg.web.jpa.HumitureRepository;
import com.scnu.lwg.web.service.HumitureService;
import com.scnu.lwg.web.util.SignUtil;
import com.scnu.lwg.web.vo.HumitureMqttVo;
import com.scnu.lwg.web.vo.HumitureVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kin
 * @description humiture service impl
 * @email kinsanities@sina.com
 * @time 2021/5/2 2:55 下午
 */

@Service
@Slf4j
public class HumitureServiceImpl implements HumitureService {
	@Resource
	HumitureRepository humitureRepository;

	@Resource
	CryptoUtils cryptoUtils;

	@Override
	public Humiture save(Humiture humiture) {
		return humitureRepository.save(humiture);
	}

	@Override
	public Humiture findCurrentHumiture() {
		return humitureRepository.findLast();
	}

	@Override
	public HumitureVo getData(String range, String time) {
		List<Humiture> data = humitureRepository.getData(range, time);
		HumitureVo vo = new HumitureVo();
		if (CollectionUtils.isEmpty(data)){
			return null;
		}
		List<Long> times = new ArrayList<>();
		List<Float> hs = new ArrayList<>();
		List<Float> ts = new ArrayList<>();
		data.forEach(h ->{
			times.add(h.getCreateTime().getTime()/1000);
			hs.add(h.getHumidity());
			ts.add(h.getTemperature());
		});
		vo.setTimes(times);
		vo.setHumidities(hs);
		vo.setTemperatures(ts);
		return vo;
	}

	@Override
	public void send(String data)  {
		String origin = cryptoUtils.wbSm4Dec(data);
		try {
			HumitureMqttVo msg = JSONUtils.jsonToObj(origin, HumitureMqttVo.class);
			Humiture h = new Humiture();
			BeanUtils.copyProperties(msg, h);
			if (!SignUtil.validateSign(h, msg.getSign())){
				log.error("sign failure. for invalid data {}", data);
				return;
			}
			humitureRepository.save(h);
			log.info("receive mqtt data : {}", data);
		} catch (IOException e) {
			log.error("invalid data: {}", data);
		}
	}
}
