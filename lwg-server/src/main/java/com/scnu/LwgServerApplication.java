package com.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Kin
 * @description light weight gateway application
 * @email kinsanities@sina.com
 * @time 2021/5/2 2:54 下午
 */
@SpringBootApplication
@EnableJpaAuditing
public class LwgServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LwgServerApplication.class, args);
	}
}
