package com.scnu.lwg.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author Kin
 * @description menu vo
 * @email kinsanities@sina.com
 * @time 2021/5/2 2:57 下午
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MenuVo {

	private Long id;

	private Long pid;

	private String title;

	private String icon;

	private String href;

	private String target;

	private List<MenuVo> child;
}