package com.bank.manager.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        String method = req.getMethod();
        System.out.println("[FILTER] Incoming request: " + method + " " + path);
        chain.doFilter(request, response);
    }

}