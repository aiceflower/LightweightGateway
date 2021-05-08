package com.scnu.lwg.config;

/**
 * @author Kin
 * @description response code and message
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

public enum ResponseCode {

	/**    uniform return code    **/

	/** success */
	SUCCESS("0", "success"),
	/** error */
	ERROR("-1", "error"),

	/**    auth return code    **/
	APP_ID_ERROR("1001", "appId is not found!"),
	APP_SECRET_ERROR("1002", "appSecret is not found!"),
	APP_SECRET_NOT_EFFECTIVE("1003", "appSecret is not effective!"),
	TOKEN_REFRESH_ERROR("1004", "need refresh token"),
	TOKEN_PARSE_ERROR("1005", "token parse error. please get token again."),
	;
	ResponseCode(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String code() {
		return code;
	}

	public String msg() {
		return msg;
	}

	private String code;
	private String msg;
}
