package com.meicloud.security.core.social.support;

/**
 * 简单响应的封装类
 *
 * @author HuaDong
 * @date 2020/4/25 23:38
 */
public class SimpleResponse {
	
	public SimpleResponse(Object content){
		this.content = content;
	}
	
	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
}
