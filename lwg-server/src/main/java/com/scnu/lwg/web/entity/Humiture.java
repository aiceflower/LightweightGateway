package com.scnu.lwg.web.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Kin
 * @description h t
 * @email kinsanities@sina.com
 * @time 2021/5/3 7:21 下午
 */
@Data
@Table( name ="ht")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Humiture implements Serializable {

	private static final long serialVersionUID =  5421757630121636116L;

	/**
	 * key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * create time
	 */
	@CreatedDate
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
