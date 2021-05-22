package com.scnu.lwg.exception;

/**
 * @author Kin
 * @description ValidateCodeException
 * @email kinsanities@sina.com
 * @time 2021/5/3 11:25 上午
 */

/**
 * 验证码异常
 * @author zlt
 */
public class ValidateCodeException extends BaseException {
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}
}
