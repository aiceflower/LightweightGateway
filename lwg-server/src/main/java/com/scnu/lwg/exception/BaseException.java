package com.scnu.lwg.exception;

import lombok.Data;

/**
 * @author Kin
 * @description base exception
 * @email kinsanities@sina.com
 * @time 2021/5/3 1:02 下午
 */
@Data
public class BaseException extends RuntimeException{
	private String code;

	private String msg;

	/**
	 * 默认异常，状态码为99
	 *
	 * @param msg
	 */
	public BaseException(String msg) {
		super(msg);
		this.msg = msg;
		this.code = "99";
	}

	/**
	 * 自定义异常
	 *
	 * @param code
	 * @param msg
	 */
	public BaseException(String code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public BaseException() {
		super("系统错误，请联系管理员");
		this.code = "99";
		this.msg = "系统错误，请联系管理员";
	}
}
