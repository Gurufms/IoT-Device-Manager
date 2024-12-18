package com.iot.server;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic can go here if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // Cast request to HttpServletRequest
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // Set current request in ThreadLocal
            RequestContextHolder.setCurrentRequest(httpRequest);
            // Continue the filter chain
            chain.doFilter(request, response);
        } finally {
            // Clear the ThreadLocal to prevent memory leaks
            RequestContextHolder.clear();
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}