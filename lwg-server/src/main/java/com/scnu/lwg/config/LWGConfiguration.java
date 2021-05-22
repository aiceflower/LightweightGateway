package com.scnu.lwg.config;

import com.scnu.lwg.interceptor.LWGTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author Kin
 * @description configuration
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */
@Configuration
public class LWGConfiguration extends WebMvcConfigurerAdapter
{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LWGTokenInterceptor()).addPathPatterns("/lwg/**");
    }

}
