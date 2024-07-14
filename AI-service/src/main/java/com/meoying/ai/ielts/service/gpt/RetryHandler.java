package com.meoying.ai.ielts.service.gpt;

import com.github.rholder.retry.*;
import com.meoying.ai.ielts.utils.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 疑问：这样实现handler   无法知道要重试的handler是谁
 */
@Service
@Slf4j
public class RetryHandler extends AbstractHandler{
    /**
     * 创建一个重试器，重试器执行的方法
     */
    private static Retryer retryer = RetryerBuilder.newBuilder()
            // 出现异常时，会重试
            .retryIfException()
            // 失败后，隔2秒后重试
            //.withWaitStrategy(WaitStrategies.fixedWait(100, TimeUnit.MILLISECONDS))
            // 重试3次后，仍未成功，就不再重试
            .withStopStrategy(StopStrategies.stopAfterAttempt(3))
            .build();
    @Resource
    private CreditHandler credit;

    @Override
    public Response handle(Request req) {
        return null;
    }

    @Override
    public Response retryHandle(Handler handler, Request req){
        return retryHandler(handler, req);
    }

    private Response retryHandler(Handler handler,Request req) {
        try {
            return (Response) retryer.call((Callable<Response>) () -> {
                return handler.handle(req);
            });
        } catch (ExecutionException | RetryException e) {
            log.warn("retryHandler error, req={}", JsonUtils.toJson(req), e);
        } catch (Exception e) {
            log.error("retryHandler error, req={}", JsonUtils.toJson(req), e);
        }
        return null;
    }
}
