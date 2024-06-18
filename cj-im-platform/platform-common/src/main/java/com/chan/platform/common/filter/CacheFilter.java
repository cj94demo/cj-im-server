package com.chan.platform.common.filter;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/18 14:48
 * FileName: CacheFilter
 * Description: 缓存过滤器
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/", filterName = "xssFilter")
public class CacheFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new CacheHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
    }
}
