package com.scnu.lwg.controller;

import com.scnu.lwg.util.CryptoUtils;
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
	OkHttpCli okHttpCli;

	@Resource
	CryptoUtils cryptoUtils;

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

	/**
	 * test sm3
	 * @param str
	 * @return
	 */
	@GetMapping(value = "/sm3")
	public String sm3(String str) {
		String s = cryptoUtils.wbSm4Enc(str);
		String plan = cryptoUtils.wbSm4Dec(s);
		return plan + " " + CryptoUtils.sm3(str) ;
	}

	/**
	 * test encrypt
	 * @param str
	 * @return
	 */
	@GetMapping(value = "/enc")
	public String enc(String str) {
		return cryptoUtils.wbSm4Enc(str);
	}

	/**
	 * test decrypt
	 * @param str
	 * @return
	 */
	@GetMapping(value = "/dec")
	public String dec(String str) {
		return cryptoUtils.wbSm4Dec(str);
	}
}
