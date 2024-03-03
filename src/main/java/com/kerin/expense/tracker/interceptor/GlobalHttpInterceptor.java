package com.kerin.expense.tracker.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class GlobalHttpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Blocking for Http request for Production ");
        log.info("[preHandle][" + request + "]" + "[" + request.getMethod()
                + "]" + request.getRequestURI());

        if(request.getScheme().equalsIgnoreCase("http") || request.getScheme().equalsIgnoreCase("")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "direct http access not allowed");
            log.info("exiting preHandle with SC_FORBIDDEN error");
            return false;
        }

        log.info("Exiting preHandle ");

        return true;
    }
}