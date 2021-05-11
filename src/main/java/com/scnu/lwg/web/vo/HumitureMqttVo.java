package com.scnu.lwg.web.vo;

import com.scnu.lwg.web.entity.Humiture;
import lombok.Data;

/**
 * @author Kin
 * @description humiture mqtt vo
 * @email kinsanities@sina.com
 * @time 2021/5/11 11:58 上午
 */

@Data
public class HumitureMqttVo extends Humiture {
	/**
	 * 签名
	 */
	private String sign;

	/**
	 * token
	 */
	private String token;
}
