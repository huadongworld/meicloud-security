package com.meicloud.security.core.validate.code;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;

import com.meicloud.security.core.properties.SecurityConstants;
import com.meicloud.security.core.validate.code.image.ImageCode;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 生成校验码的请求处理器
 *
 * @author HuaDong
 * @date 2020/5/11 22:54
 */
@RestController
public class ValidateCodeController {

	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	private static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	private static final String SMS_CODE_SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

	/**
	 * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
	 *
	 * @param request
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
	}

	@GetMapping("/code/image")
	public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ImageCode imageCode = createCodeImageCode(request);
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}

//	@GetMapping("/code/sms")
//	public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		// 创建验证码
//		ValidateCode smsCode = createCodeSmsCode(request);
//		// 将验证码放到session中
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SMS_CODE_SESSION_KEY, smsCode);
//		String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
//		// 发送验证码
//		smsCodeSender.send(mobile, smsCode.getCode());
//	}

	private ImageCode createCodeImageCode(HttpServletRequest request) {
		return null;
	}
}
