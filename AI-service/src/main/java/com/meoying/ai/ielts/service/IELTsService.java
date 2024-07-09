package com.meoying.ai.ielts.service;

import com.meoying.ai.ielts.service.gpt.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class IELTsService {
    private Handler root;

    private String scorePrompt;
    private String analysisPrompt;
    private String referencePrompt;

    public IELTsService(
            @Value("classpath:prompt_1.md")
            Resource scorePrompt,
            @Value("classpath:prompt_2.md")
            Resource analysisPrompt,
            @Value("classpath:prompt_3.md")
            Resource referencePrompt,
            CreditHandler credit,
            GPTRecordHandler record,
            ZhipuHandler zhipu) throws IOException {
        this.scorePrompt = scorePrompt.getContentAsString(Charset.defaultCharset());
        this.analysisPrompt = analysisPrompt.getContentAsString(Charset.defaultCharset());
        this.referencePrompt = referencePrompt.getContentAsString(Charset.defaultCharset());
        this.root = credit;
        credit.setNext(record);
        record.setNext(zhipu);
    }

    public WritingAnswer checkWriting(Request req) {
        Request scoreReq = Request.builder().prompt(scorePrompt).input(req.getInput()).build();
        Request analysisReq = Request.builder().prompt(analysisPrompt).input(req.getInput()).build();
        Request referenceReq = Request.builder().prompt(referencePrompt).input(req.getInput()).build();

        Response scoreResp = root.handle(scoreReq);
        Response analysisResp = root.handle(analysisReq);
        Response referenceResp = root.handle(referenceReq);

        return WritingAnswer.builder()
                .score(scoreResp.getAnswer())
                .analysis(analysisResp.getAnswer())
                .reference(referenceResp.getAnswer())
                .amount(scoreResp.getAmount()+ analysisResp.getAmount() + referenceResp.getAmount())
                .tokens(scoreResp.getTokens() + analysisResp.getTokens() + referenceResp.getTokens())
                .build();
    }

    // 转成 json 放进去 Response 中
    @lombok.Builder
    public static class WritingAnswer {
        // 评分，对应于 prompt-1
        private String score;
        // 分析和修改，对应于 prompt-2
        private String analysis;
        // 范文，对应于 prompt-3
        private String reference;

        // 花了多少钱
        private long amount;
        // 使用了多少 tokens
        private long tokens;
    }
}
