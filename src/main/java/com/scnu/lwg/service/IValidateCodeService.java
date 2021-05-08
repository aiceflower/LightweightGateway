package com.scnu.lwg.service;

/**
 * @author Kin
 * @description Validate Code Service
 * @email kinsanities@sina.com
 * @time 2021/5/3 11:22 上午
 */

public interface IValidateCodeService {
	/**
	 * 保存图形验证码
	 * @param deviceId 前端唯一标识
	 * @param imageCode 验证码
	 */
	void saveImageCode(String deviceId, String imageCode);

	/**
	 * 获取验证码
	 * @param deviceId 前端唯一标识/手机号
	 */
	String getCode(String deviceId);

	/**
	 * 删除验证码
	 * @param deviceId 前端唯一标识/手机号
	 */
	void remove(String deviceId);

	/**
	 * 验证验证码
	 * @param deviceId
	 * @param validCode
	 */
	void validate(String deviceId, String validCode);
}
