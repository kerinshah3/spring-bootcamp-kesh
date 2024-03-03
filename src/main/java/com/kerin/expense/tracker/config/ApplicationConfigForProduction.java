package com.kerin.expense.tracker.config;

import com.kerin.expense.tracker.constant.ApplicationConstant;
import com.kerin.expense.tracker.interceptor.GlobalHttpInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Profile({ApplicationConstant.ENV_PROD})
@Slf4j
@Component
public class ApplicationConfigForProduction extends WebMvcConfigurationSupport {

    @Autowired
    GlobalHttpInterceptor globalHttpInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("***** http block interceptor setup *****");

        registry.addInterceptor(globalHttpInterceptor).addPathPatterns("/rest/*");
    }
}