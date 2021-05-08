package com.scnu.lwg.controller;

import com.scnu.lwg.config.Result;
import com.scnu.lwg.mqtt.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
	public Result pub(String msg){
		producer.sendMsg(msg);
		return Result.success(null);
	}
}
