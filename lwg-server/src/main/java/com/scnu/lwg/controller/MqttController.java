package com.scnu.lwg.controller;

import com.scnu.lwg.config.Result;
import com.scnu.lwg.mqtt.Producer;
import com.scnu.lwg.util.JSONUtils;
import com.scnu.lwg.web.vo.HumitureMqttVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Kin
 * @description mqtt controller
 * @email kinsanities@sina.com
 * @time 2021/5/3 2:07 下午
 */

@RestController
@RequestMapping(value = "/lwg/mqtt")
public class MqttController {

	@Resource
	Producer producer;

	@GetMapping("/pub")
	public Result pub(String msg, HttpServletRequest req){

		String token = req.getHeader("X-LWG-Token");

		HumitureMqttVo h = new HumitureMqttVo();
		h.setDeviceId("testDevice");
		h.setCreateTime(new Date());
		h.setHumidity(48f);
		h.setTemperature(26.3f);
		h.setToken(token);
		String data = JSONUtils.objToJson(h);
		producer.sendMsg(data);
		return Result.success(null);
	}

}
