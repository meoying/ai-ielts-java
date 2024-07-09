package com.meoying.ai.ielts.service.gpt;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 加这个的原因：调用gpt后的结果  需要落库
 * gpt的结果需要存到上下文中，以便后续的落库
 */
@Slf4j
public class HandlerContext extends ConcurrentHashMap<String, Object> {
    /**
     * 子类初始化的时候 父类也要初始化
     */
    protected static Class<? extends HandlerContext> contextClass = HandlerContext.class;


    /**
     * 线程隔离
     */
    protected static final TransmittableThreadLocal<? extends HandlerContext> threadLocal = new TransmittableThreadLocal<HandlerContext>() {
        @Override
        protected HandlerContext initialValue() {
            try {
                return contextClass.newInstance();
            } catch (Exception e) {
                log.error("contextClass.newInstance() error", e);
                throw new RuntimeException(e);
            }
        }
    };


    /**
     * 获取当前线程的 context
     */
    public static HandlerContext getCurrentContext() {
        HandlerContext context = threadLocal.get();
        return context;
    }

    /**
     * 线程结束后要回收context
     */
    public void unset() {
        threadLocal.remove();
    }

    public void set(String key, Object value) {
        if (value != null) {
            put(key, value);
        } else {
            remove(key);
        }
    }

    public void setZhiPuResponse(Response req) {
        set("zhiPuRes", req);
    }

    public Response getZhiPuResponse() {
        return (Response)get("zhiPuRes");
    }
}
