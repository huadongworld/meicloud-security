package com.meicloud.web.config;

import com.meicloud.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器：创建一个config类，继承WebMvcConfigurerAdapter
 *
 * @author HuaDong
 * @date 2020/4/11 19:32
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private TimeInterceptor timeInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeInterceptor);
	}
}
