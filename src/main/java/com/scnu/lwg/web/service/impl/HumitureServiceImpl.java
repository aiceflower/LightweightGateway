package com.scnu.lwg.web.service.impl;

import com.scnu.lwg.web.entity.Humiture;
import com.scnu.lwg.web.jpa.HumitureRepository;
import com.scnu.lwg.web.service.HumitureService;
import com.scnu.lwg.web.vo.HumitureVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kin
 * @description humiture service impl
 * @email kinsanities@sina.com
 * @time 2021/5/2 2:55 下午
 */

@Service
public class HumitureServiceImpl implements HumitureService {
	@Resource
	HumitureRepository humitureRepository;

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
}
