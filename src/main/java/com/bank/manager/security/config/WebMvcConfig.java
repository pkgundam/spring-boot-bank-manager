package com.bank.manager.security.config;

import com.bank.manager.security.interceptors.RequestTimingInterceptor;
import com.bank.manager.security.interceptors.UserActivityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RequestTimingInterceptor timingInterceptor;
    private final UserActivityInterceptor activityInterceptor;

    public WebMvcConfig(RequestTimingInterceptor timingInterceptor,
                        UserActivityInterceptor activityInterceptor) {
        this.timingInterceptor = timingInterceptor;
        this.activityInterceptor = activityInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timingInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(activityInterceptor)
                .addPathPatterns("/api/**");
    }

}