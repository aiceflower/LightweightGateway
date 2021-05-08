package com.scnu.lwg.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Kin
 * @description json util
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:44 下午
 */

public abstract class JSONUtils {

	public static String objToJson(Object o){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
