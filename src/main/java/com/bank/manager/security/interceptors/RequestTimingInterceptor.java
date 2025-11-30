package com.bank.manager.security.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestTimingInterceptor implements HandlerInterceptor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        startTime.set(System.currentTimeMillis());
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {
        long duration = System.currentTimeMillis() - startTime.get();
        System.out.println("[INTERCEPTOR] Request to " +
                request.getRequestURI() + " took " + duration + "ms");
        startTime.remove();
    }

}