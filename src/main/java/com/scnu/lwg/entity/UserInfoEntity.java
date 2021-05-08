package com.scnu.lwg.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Kin
 * @description userinfo entity
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

@Entity
@Table(name = "api_user_infos")
@Data
public class UserInfoEntity implements Serializable
{
    @Id
    @Column(name = "aui_app_id")
    private String appId;

    @Column(name = "aui_app_secret")
    private String appSecret;

    @Column(name = "aui_status")
    private int status;

    @Column(name = "aui_day_request_count")
    private int dayRequestCount;

    @Column(name = "aui_ajax_bind_ip")
    private String ajaxBindIp;

    @Column(name = "aui_mark")
    private String mark;
}
