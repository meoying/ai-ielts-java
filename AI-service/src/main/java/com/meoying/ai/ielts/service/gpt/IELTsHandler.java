package com.meoying.ai.ielts.service.gpt;

// IELTsHandler 并发发出三个请求，每个请求使用不同的 prompt
// 所以你在这里你不会使用 req 里面的 prompt
// 而后你会合并三个 resp 为一个
public class IELTsHandler implements Handler{
    @Override
    public Response handle(Request req, HandlerContext context) {
        return null;
    }

    // 转成 json 放进去 Response 中
    private static class Answer {
        // 评分，对应于 prompt-1
        private String score;
        // 分析和修改，对应于 prompt-2
        private String analysis;
        // 范文，对应于 prompt-3
        private String reference;
    }
}