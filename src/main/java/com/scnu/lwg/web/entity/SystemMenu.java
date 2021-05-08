package com.scnu.lwg.web.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Kin
 * @description system menu
 * @email kinsanities@sina.com
 * @time 2021/5/2 3:00 下午
 */


@Entity
@Table( name ="system_menu")
@DynamicInsert
@DynamicUpdate
@Data
public class SystemMenu  implements Serializable {

	private static final long serialVersionUID =  5421757630121636006L;

	/**复合主键要用这个注解*/
	@EmbeddedId
	private MenuKey key;

	/**
	 * 父ID
	 */
	@Column(name = "pid" )
	private Long pid;

	/**
	 * 菜单图标
	 */
	@Column(name = "icon")
	private String icon;

	/**
	 * 链接打开方式
	 */
	@Column(name = "target",columnDefinition = "_self")
	private String target;
	/**
	 * 菜单排序
	 */
	@Column(name = "sort" )
	private Long sort;

	/**
	 * 状态(0:禁用,1:启用)
	 */
	@Column(name = "status",columnDefinition = "tinyint DEFAULT 1")
	private Integer status;

	/**
	 * 备注信息
	 */
	@Column(name = "remark" )
	private String remark;

	/**
	 * 创建时间
	 */
	@Column(name = "create_at" )
	private Date createAt;

	/**
	 * 更新时间
	 */
	@Column(name = "update_at" )
	private Date updateAt;

	/**
	 * 删除时间
	 */
	@Column(name = "delete_at" )
	private Date deleteAt;
}
