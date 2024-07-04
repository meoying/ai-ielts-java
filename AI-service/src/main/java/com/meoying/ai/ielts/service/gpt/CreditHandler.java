package com.meoying.ai.ielts.service.gpt;

// CreditHandler 处理余额有关的事情
public class CreditHandler implements Handler{
    private Handler next;

    // 多少钱 / 1000 tokens
    private long price;
    @Override
    public Response handle(Request req) {
        // 1. 验证用户的余额大于 0，但是没办法验证够不够

        // 执行
        Response resp = next.handle(req);

        // 2. 扣减 tokens 对应的余额，也就是 Account 中的 balance
        return null;
    }
}
