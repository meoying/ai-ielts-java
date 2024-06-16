package com.meoying.ai.ielts.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

//打印方法出入口日志
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AccessLog {
    /**
     * 自定义前缀
     * @return
     */
    String preLog() default "";
}
