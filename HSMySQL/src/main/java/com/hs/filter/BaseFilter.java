package com.hs.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
public abstract class BaseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	String userAgent = ((HttpServletRequest)request).getHeader("User-Agent");
    	String clientIP = ((HttpServletRequest)request).getRemoteAddr();
    	String realIP = ((HttpServletRequest)request).getHeader("x-forwarded-for");
    	System.out.printf("client 端連線設備: %s%n", userAgent);
    	System.out.printf("client 端 clientIP: %s%n", clientIP);
    	System.out.printf("client 端 realIP: %s%n", realIP);
    	
    	doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }
    
    protected abstract void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException;

    @Override
    public void destroy() {
        
    }
}
