package com.meicloud.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meicloud.security.core.properties.SecurityProperties;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * 默认的session失效处理策略
 *
 * @author HuaDong
 * @date 2020/5/31 23:46
 */
public class MeicloudInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {

	public MeicloudInvalidSessionStrategy(SecurityProperties securityProperties) {
		super(securityProperties);
	}

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

}
