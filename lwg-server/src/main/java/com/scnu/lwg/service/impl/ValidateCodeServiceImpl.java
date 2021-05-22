package com.scnu.lwg.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.scnu.lwg.config.Constants;
import com.scnu.lwg.exception.ValidateCodeException;
import com.scnu.lwg.service.IValidateCodeService;
import com.scnu.lwg.util.CacheUtils;
import org.springframework.stereotype.Service;

/**
 * @author Kin
 * @description Validate Code Service Impl
 * @email kinsanities@sina.com
 * @time 2021/5/3 11:24 上午
 */

@Service
public class ValidateCodeServiceImpl implements IValidateCodeService {
	/**
	 * 保存用户验证码，和randomStr绑定
	 *
	 * @param deviceId 客户端生成
	 * @param imageCode 验证码信息
	 */
	@Override
	public void saveImageCode(String deviceId, String imageCode) {
		CacheUtils.putCode(buildKey(deviceId), imageCode);
	}

	/**
	 * 获取验证码
	 * @param deviceId 前端唯一标识/手机号
	 */
	@Override
	public String getCode(String deviceId) {
		return (String)CacheUtils.getCode(buildKey(deviceId));
	}

	/**
	 * 删除验证码
	 * @param deviceId 前端唯一标识/手机号
	 */
	@Override
	public void remove(String deviceId) {
		CacheUtils.delCode(buildKey(deviceId));
	}

	/**
	 * 验证验证码
	 */
	@Override
	public void validate(String deviceId, String validCode) {
		if (StringUtils.isEmpty(deviceId)) {
			throw new ValidateCodeException("请在请求参数中携带deviceId参数");
		}
		String code = this.getCode(deviceId);
		if (StringUtils.isEmpty(validCode)) {
			throw new ValidateCodeException("请填写验证码");
		}

		if (code == null) {
			throw new ValidateCodeException("验证码不存在或已过期");
		}

		if (!StringUtils.equals(code, validCode.toLowerCase())) {
			throw new ValidateCodeException("验证码不正确");
		}
		this.remove(deviceId);
	}

	private String buildKey(String deviceId) {
		return Constants.DEFAULT_CODE_KEY + ":" + deviceId;
	}
}
