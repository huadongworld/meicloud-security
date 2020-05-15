package com.meicloud.security.browser;

import com.meicloud.security.core.properties.SecurityProperties;
import com.meicloud.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 浏览器环境下安全配置主类
 *
 * @author HuaDong
 * @date 2020/4/11 21:45
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private AuthenticationSuccessHandler meicloudAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler meicloudAuthenticationFailureHandler;


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 验证码校验过滤器
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		// 将验证码校验过滤器加到 UsernamePasswordAuthenticationFilter 过滤器之前
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.formLogin()
				// 当用户登录认证时默认跳转的页面
				.loginPage("/authentication/require")
				// 以下这行 UsernamePasswordAuthenticationFilter 会知道要处理表单的 /authentication/form 请求，而不是默认的 /login
				.loginProcessingUrl("/authentication/form")
				.successHandler(meicloudAuthenticationSuccessHandler)
				.failureHandler(meicloudAuthenticationFailureHandler)
				.and()
				.authorizeRequests()
				// 排除对 "/authentication/require" 和 "/meicloud-signIn.html" 的身份验证
				.antMatchers("/authentication/require", securityProperties.getBrowser().getSignInPage(), "/code/image").permitAll()
				// 表示所有请求都需要身份验证
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable();// 暂时把跨站请求伪造的功能关闭掉

	}
}
