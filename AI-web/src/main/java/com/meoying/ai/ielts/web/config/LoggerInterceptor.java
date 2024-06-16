package com.meoying.ai.ielts.web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;


@Slf4j
@Component
public class LoggerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("{} 路径调用开始， 方法：{} , 入参： {}", request.getRequestURI(), handler, request.getParameterMap());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("{} 路径调用结束， 方法：{} , 出参： {}", request.getRequestURI(), handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(Objects.nonNull(ex)){
            log.warn("{} 路径调用出现异常！方法：{} , 入参： {}, 异常 : {}",request.getRequestURI() , handler, ex.getMessage());
        }

    }
}
