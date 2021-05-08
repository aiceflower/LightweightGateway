package com.scnu.lwg.controller;

import com.scnu.lwg.jni.JniTest;
import com.scnu.lwg.util.OkHttpCli;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Kin
 * @description jni test controller
 * @email kinsanities@sina.com
 * @time 2021/4/25 12:57 上午
 */

@RestController
@RequestMapping(value = "/test")
public class TestController {

	@Resource
	JniTest jt;

	@Resource
	OkHttpCli okHttpCli;

	/**
	 * test jni
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	@GetMapping(value = "/add")
	public Integer add(int x, int y) {
		int res = jt.add(x, y);
		return res;
	}

	/**
	 * test okhttp3
	 * @return
	 */
	@GetMapping(value = "/http")
	public String testFeign() {
		String url = "https://api.github.com/users/aiceflower";
		String message = okHttpCli.doGet(url);
		return message;
	}

}
