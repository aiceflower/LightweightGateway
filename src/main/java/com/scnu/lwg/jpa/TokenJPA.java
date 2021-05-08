package com.scnu.lwg.jpa;

import com.scnu.lwg.entity.TokenInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Kin
 * @description token jpa
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

public interface TokenJPA extends JpaRepository<TokenInfoEntity,String>,
        JpaSpecificationExecutor<TokenInfoEntity> {
}
