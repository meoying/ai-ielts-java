package com.meoying.ai.ielts.service.gpt;

import com.meoying.ai.ielts.utils.JsonUtils;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

// IELTsHandler 并发发出三个请求，每个请求使用不同的 prompt
// 所以你在这里你不会使用 req 里面的 prompt
// 而后你会合并三个 resp 为一个
public class IELTsHandler implements Handler{
    private Handler next;

    // 从配置文件里面读取
    private String scorePrompt;
    private String analysisPrompt;
    private String referencePrompt;
    @Override
    public Response handle(Request req) {
        Request scoreReq = new Request(this.scorePrompt, req.getInput());
        Request analysisReq = new Request(this.analysisPrompt, req.getInput());
        Request referenceReq = new Request(this.referencePrompt, req.getInput());

        Response scoreResp = next.handle(scoreReq);
        Response analysisResp = next.handle(analysisReq);
        Response referenceResp = next.handle(referenceReq);
        Answer answer = Answer.builder()
                .score(scoreResp.getAnswer())
                .analysis(analysisResp.getAnswer())
                .reference(referenceResp.getAnswer())
                .build();
        return Response.builder()
                .amount(scoreResp.getAmount()+ analysisResp.getAmount() + referenceResp.getAmount())
                .tokens(scoreResp.getTokens() + analysisResp.getTokens() + referenceResp.getTokens())
                .answer(JsonUtils.toJson(answer)).build();
    }

    // 转成 json 放进去 Response 中
    @lombok.Builder
    private static class Answer {
        // 评分，对应于 prompt-1
        private String score;
        // 分析和修改，对应于 prompt-2
        private String analysis;
        // 范文，对应于 prompt-3
        private String reference;
    }

    public static class Builder implements HandlerBuilder {

        @Override
        public Handler build(Handler next) {
            // JAVA 这种怎么读取配置？
            IELTsHandler handler = new IELTsHandler();
            handler.next= next;
//            handler.analysisPrompt =
            return handler;
        }
    }
}