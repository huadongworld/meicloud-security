package com.meicloud.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器演示
 *
 * @author HuaDong
 * @date 2020/4/11 19:21
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {

    /**
     * 请求Controller方法之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle");
		
		System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
		System.out.println(((HandlerMethod)handler).getMethod().getName());
		
		request.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}

    /**
     * 请求Controller方法之后
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle");
		Long start = (Long) request.getAttribute("startTime");
		System.out.println("time interceptor 耗时:"+ (System.currentTimeMillis() - start));
	}

    /**
     * 抛出异常之后 postHandle 方法不会被调用，无论是否抛出异常 afterCompletion 方法都会被调用
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion");
		Long start = (Long) request.getAttribute("startTime");
		System.out.println("time interceptor 耗时:"+ (System.currentTimeMillis() - start));
		System.out.println("ex is "+ex);
	}
}
