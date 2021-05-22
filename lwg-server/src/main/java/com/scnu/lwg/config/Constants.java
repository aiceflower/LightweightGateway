package com.scnu.lwg.config;

/**
 * @author Kin
 * @description constant
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

public abstract class Constants {

	/**  uniform constant  **/
	public static String APP_ID = "appId";
	public static String ON_LINE_BUILDER = "Online lwg Builder";
	public static String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";

	/**  request constant  **/
	public static String GEN_TOKEN = "/lwg/token";
	public static String MQTT_PUB = "/lwg/mqtt/pub";
	public static String TEST = "/test";
	public static String TOKEN = "token";
	public static String TOKEN_HEADER = "X-LWG-Token";
	public static String SIGNING_KEY = "LightweightGateway1.0.0";


	/**  exception constant  **/
	public static String NOT_FOUND_TOKEN = "not found X-LWG-Token.";
	public static String NOT_FOUND_TOKEN_INFO = "not found token info, please get token again.";

}
