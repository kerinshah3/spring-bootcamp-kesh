package com.kerin.expense.tracker.config;

import com.kerin.expense.tracker.constant.ApplicationConstant;
import com.kerin.expense.tracker.interceptor.GlobalMetaDataInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Slf4j
@Profile({ApplicationConstant.ENV_LOCAL, ApplicationConstant.ENV_DEFAULT})
@Component
public class ApplicationConfigForLocal extends WebMvcConfigurationSupport {
    @Autowired
    GlobalMetaDataInterceptor globalMetaDataInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalMetaDataInterceptor).addPathPatterns("/rest/**");
    }
}