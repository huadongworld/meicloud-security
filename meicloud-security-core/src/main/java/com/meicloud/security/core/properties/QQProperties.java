/**
 * 
 */
package com.meicloud.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author HuaDong
 * @date 2020/5/10 20:05
 */
public class QQProperties extends SocialProperties {
	
	/**
	 * 第三方id，用来决定发起第三方登录的url，默认是 qq。
	 */
	private String providerId = "qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
}
