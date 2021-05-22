package com.scnu.lwg.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kin
 * @description uniform return result
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

@Data
public class Result<T> implements Serializable {

	/**
	 * return code: 0-success other-error
	 */
	private String code;
	/**
	 * return message
	 */
	private String msg;
	/**
	 * return data
	 */
	private T data;

	public static Result success(Object data) {
		return resultData(ResponseCode.SUCCESS.code(), ResponseCode.SUCCESS.msg(), data);
	}

	public static Result success(Object data, String msg) {
		return resultData(ResponseCode.SUCCESS.code(), msg, data);
	}

	public static Result fail() {
		return resultData(ResponseCode.ERROR.code(), ResponseCode.ERROR.msg(), null);
	}

	public static Result fail(ResponseCode response) {
		return resultData(response.code(), response.msg(), null);
	}

	public static Result fail(String code, String msg) {
		return resultData(code, msg, null);
	}

	public static Result fail(String code, String msg, Object data) {
		return resultData(code, msg, data);
	}

	/**
	 * constructor
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	private static Result resultData(String code, String msg, Object data) {
		Result resultData = new Result();
		resultData.setCode(code);
		resultData.setMsg(msg);
		resultData.setData(data);
		return resultData;
	}
}
