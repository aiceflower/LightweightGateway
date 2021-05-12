package com.scnu.lwg.controller;

import com.scnu.lwg.service.IValidateCodeService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kin
 * @description Validate Code Controller
 * @email kinsanities@sina.com
 * @time 2021/5/3 11:20 上午
 */

@Controller()
@RequestMapping(value = "/lwg/validate")
public class ValidateCodeController {

	@Resource
	IValidateCodeService validateCodeService;

	/**
	 * 创建验证码
	 *
	 * @throws Exception
	 */
	@GetMapping("/code/{deviceId}")
	public void createCode(@PathVariable String deviceId, HttpServletResponse response) throws Exception {
		Assert.notNull(deviceId, "机器码不能为空");
		response.setContentType("image/png");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 三个参数分别为宽、高、位数
		/// GifCaptcha gifCaptcha = new GifCaptcha(100, 35, 4);
		// 换成静态图片
		SpecCaptcha specCaptcha = new SpecCaptcha(100, 35, 4);
		// 设置类型：字母数字混合
		/// gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);
		specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
		/// 保存验证码
		/// validateCodeService.saveImageCode(deviceId, gifCaptcha.text().toLowerCase());
		validateCodeService.saveImageCode(deviceId, specCaptcha.text().toLowerCase());
		// 输出图片流
		specCaptcha.out(response.getOutputStream());
	}
}
