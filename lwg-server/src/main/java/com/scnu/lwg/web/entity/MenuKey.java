package com.scnu.lwg.web.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

/**
 * @author Kin
 * @description menu key
 * @email kinsanities@sina.com
 * @time 2021/5/2 2:58 下午
 */

@Embeddable
@Data
public class MenuKey implements Serializable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	/**
	 * 名称
	 */
	@Column(name = "title")
	private String title;
	/**
	 * 链接
	 */
	@Column(name = "href")
	private String href;

}