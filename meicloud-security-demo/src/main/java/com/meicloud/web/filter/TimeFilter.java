/**
 * 
 */
package com.meicloud.web.filter;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 过滤器演示
 *
 * @author HuaDong
 * @date 2020/4/11 18:38
 */
@Component
public class TimeFilter implements Filter {

    /**
     * 容器销毁的时候调用
     */
    @Override
    public void destroy() {
//        System.out.println("time filter destroy");
    }

    /**
     * Web服务器每次在调用Web资源之前，都会先调用filter的doFilter方法
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//        System.out.println("time filter start");
//        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
//        System.out.println("time filter 耗时:" + (System.currentTimeMillis() - start));
//        System.out.println("time filter finish");
    }

    /**
     * 容器启动时候调用
     *
     * @param arg0
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
//        System.out.println("time filter init");
    }
}
