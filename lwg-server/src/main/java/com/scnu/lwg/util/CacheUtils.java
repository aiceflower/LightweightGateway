package com.scnu.lwg.util;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kin
 * @description cache utils
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

public abstract class CacheUtils {
	/** user cache*/
	private static Map<String, Object> userCache = new HashMap<>();
	/** token cache*/
	private static Map<String, Object> tokenCache = new HashMap<>();
	/** code cache*/
	private static Map<String, Object> codeCache = new HashMap<>();

	public static synchronized int putUser(String k, Object v){
		assert k != null;
		assert v != null;
		userCache.put(k, v);
		return userCache.size();
	}

	public static synchronized Object getUser(String k){
		assert k != null;
		return userCache.get(k);
	}

	public static synchronized  <T> T getUser(Class<T> clazz, String k)
	{
		Object res = userCache.get(k);
		if (res == null){return null;}
		T result = clazz.cast(res);
		return result;
	}

	public static synchronized boolean hasUser(String k){
		if (StringUtils.isEmpty(k)){
			return Boolean.FALSE;
		}
		return userCache.containsKey(k);
	}

	public static synchronized int putToken(String k, Object v){
		assert k != null;
		assert v != null;
		tokenCache.put(k, v);
		return tokenCache.size();
	}

	public static synchronized Object getToken(String k){
		assert k != null;
		return tokenCache.get(k);
	}

	public static synchronized  <T> T getToken(Class<T> clazz, String k)
	{
		Object res = tokenCache.get(k);
		if (res == null){return null;}
		T result = clazz.cast(res);
		return result;
	}

	public static synchronized boolean hasToken(String k){
		if (StringUtils.isEmpty(k)){
			return Boolean.FALSE;
		}
		return tokenCache.containsKey(k);
	}

	public static synchronized int putCode(String k, Object v){
		assert k != null;
		assert v != null;
		codeCache.put(k, v);
		return codeCache.size();
	}

	public static synchronized Object getCode(String k){
		assert k != null;
		return codeCache.get(k);
	}

	public static synchronized void delCode(String k){
		assert k != null;
		codeCache.remove(k);
	}
}
