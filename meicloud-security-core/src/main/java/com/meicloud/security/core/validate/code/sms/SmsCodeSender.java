package com.meicloud.security.core.validate.code.sms;

/**
 * @author HuaDong
 * @date 2020/5/11 22:57
 */
public interface SmsCodeSender {
	
	/**
	 * @param mobile
	 * @param code
	 */
	void send(String mobile, String code);

}
