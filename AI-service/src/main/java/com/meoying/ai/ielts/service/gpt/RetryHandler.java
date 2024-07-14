package com.meoying.ai.ielts.service.gpt;

import com.github.rholder.retry.*;
import com.meoying.ai.ielts.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;


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

    @Override
    public Response handle(Request req) {
        return null;
    }

    public Response retryHandler(Request req) {
        try {
            return (Response) retryer.call((Callable<Response>) () -> {
                return next.handle(req);
            });
        } catch (ExecutionException | RetryException e) {
            log.warn("retryHandler error, req={}", JsonUtils.toJson(req), e);
        } catch (Exception e) {
            log.error("retryHandler error, req={}", JsonUtils.toJson(req), e);
        }
        return null;
    }
}
