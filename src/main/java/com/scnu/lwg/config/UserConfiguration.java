package com.scnu.lwg.config;

import com.scnu.lwg.util.CacheUtils;
import com.scnu.lwg.entity.UserInfoEntity;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Kin
 * @description user info configuration
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */

@Component
@ConfigurationProperties(prefix = "lwg")
@Data
public class UserConfiguration {
	List<UserInfoEntity> users;

	/**
	 * init cache
	 */
	@PostConstruct
	private void initCache(){
		if (!users.isEmpty()){
			for (UserInfoEntity user : users) {
				CacheUtils.putUser(user.getAppId(), user);
			}
		}
	}
}
