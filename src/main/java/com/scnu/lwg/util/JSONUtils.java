package com.scnu.lwg.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

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

	public static <T> T jsonToObj(String json, Class<T> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		// 转换为格式化的json
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		// 如果json中有新增的字段并且是实体类类中不存在的，不报错
		/// mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T res = mapper.readValue(json, clazz);
		return res;
	}
}
