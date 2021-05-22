package com.scnu.lwg.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Kin
 * @description h t
 * @email kinsanities@sina.com
 * @time 2021/5/3 7:21 下午
 */
@Data
public class Humiture implements Serializable {

	private static final long serialVersionUID =  5421757630121636116L;

	/**
	 * key
	 */
	private Long id;

	/**
	 * create time
	 */
	private Date createTime;

	/**
	 * location
	 */
	private String site;

	/**
	 * temperature
	 */
	private Float temperature;

	/**
	 * humidity
	 */
	private Float humidity;

	/**
	 * device id
	 */
	private String deviceId;

	/**
	 * gateway id
	 */
	private String gatewayId;

}
