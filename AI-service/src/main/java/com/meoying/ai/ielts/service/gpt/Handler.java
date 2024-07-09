package com.meoying.ai.ielts.service.gpt;

public interface Handler {
    Response handle(Request req);
    // 用于构建链，根据需要自己决定要不要设置 next
    void setNext(Handler next);
}