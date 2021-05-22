package com.scnu.lwg.jpa;

import com.scnu.lwg.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Kin
 * @description userinfo jpa
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

public interface UserInfoJPA extends JpaRepository<UserInfoEntity,String>,
        JpaSpecificationExecutor<UserInfoEntity>
{

}
