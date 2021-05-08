package com.scnu.lwg.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Kin
 * @description token entity
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

@Entity
@Table(name = "api_token_infos")
@Data
public class TokenInfoEntity implements Serializable
{
    @Id
    @GeneratedValue
    @Column(name = "ati_id")
    private Long id;
    @Column(name = "ati_app_id")
    private  String appId;
    @Column(name = "ati_token")
    private byte[] token;
    @Column(name = "ati_build_time")
    private String buildTime;
}
