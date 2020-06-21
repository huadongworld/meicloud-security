package com.meicloud.security.browser;

import com.meicloud.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.meicloud.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.meicloud.security.core.properties.SecurityProperties;
import com.meicloud.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	private AuthenticationSuccessHandler meicloudAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler meicloudAuthenticationFailureHandler;

	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

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
				// 配置记住我功能
				.and()
				.rememberMe()
					// 配置TokenRepository
					.tokenRepository(persistentTokenRepository())
					// 配置Token过期时间
					.tokenValiditySeconds(3600)
					// 最终拿到用户名之后，使用UserDetailsService去做登录
					.userDetailsService(userDetailsService)
					.and()
				.authorizeRequests()
					// 排除对 "/authentication/require" 和 "/meicloud-signIn.html" 的身份验证
					.antMatchers("/authentication/require", securityProperties.getBrowser().getSignInPage(), "/code/*").permitAll()
					// 表示所有请求都需要身份验证
					.anyRequest()
					.authenticated()
					.and()
				.logout()
					// 配置推出的登录接口
					.logoutUrl("/signOut")
					// 退出登录后跳到的页面
//					.logoutSuccessUrl("meicloud-logout.html")
					// 配置登出成功处理器
					.logoutSuccessHandler(logoutSuccessHandler)
					.deleteCookies("JSESSIONID")
					.and()
				.csrf().disable()// 暂时把跨站请求伪造的功能关闭掉
				// 相当于把smsCodeAuthenticationSecurityConfig里的配置加到上面这些配置的后面
				.apply(smsCodeAuthenticationSecurityConfig);
	}

	/**
	 * 记住我功能的Token存取器配置
	 *
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		// 启动的时候自动创建表，建表语句 JdbcTokenRepositoryImpl 已经都写好了
		tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}
}
