package com.scnu.lwg.web.util;

import com.scnu.lwg.util.CryptoUtils;
import com.scnu.lwg.web.entity.Humiture;

/**
 * @author Kin
 * @description sign util
 * @email kinsanities@sina.com
 * @time 2021/5/11 12:14 下午
 */

public class SignUtil {
	/**
	 * sign
	 * @param h
	 * @return
	 */
	public static String sign(Humiture h){
		StringBuilder sb = new StringBuilder();
		sb.append(h.getDeviceId() + h.getCreateTime() + h.getHumidity() + h.getTemperature());
		return CryptoUtils.sm3(sb.toString());
	}

	/**
	 * validate sign
	 * @param h
	 * @param sign
	 * @return
	 */
	public static boolean validateSign(Humiture h, String sign){
		return sign(h).equals(sign);
	}
}
