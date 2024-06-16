package com.meoying.ai.ielts.aop;

import com.meoying.ai.ielts.annotation.AccessLog;
import com.meoying.ai.ielts.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AccessLogAdvice {

    @Around(value = "@annotation(accessLog)")
    public Object invoke(ProceedingJoinPoint pjp, AccessLog accessLog) throws Throwable {
        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        String method = ((MethodSignature)pjp.getSignature()).getMethod().getName();
        String preLog = accessLog.preLog();
        Object[] args = pjp.getArgs();
        log.info(preLog+" start !className = "+className+", methodName = "+method+", params = "+ JsonUtils.toJson(args));

        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        log.info(preLog+"  end ! "+className+" , methodName = "+method+", runtime = "+(endTime - startTime)+"ms, result="+JsonUtils.toJson(result));

        return result;
    }
}
