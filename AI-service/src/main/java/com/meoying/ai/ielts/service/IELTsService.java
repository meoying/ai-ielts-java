package com.meoying.ai.ielts.service;

import com.meoying.ai.ielts.dao.WritingDAO;
import com.meoying.ai.ielts.dao.entity.WritingEntity;
import com.meoying.ai.ielts.service.gpt.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

@Service
public class IELTsService {
    private final Handler root;
    @jakarta.annotation.Resource
    private WritingDAO writingDAO;

    private final static String writingExamineBiz = "writing_examine";

    private final String scorePromptTpl;
    private final String analysisPromptTpl;
    private final String referencePromptTpl;

    public IELTsService(
            @Value("classpath:promptTpl/writing_score.md")
            Resource scorePromptTpl,
            @Value("classpath:promptTpl/writing_analysis.md")
            Resource analysisPromptTpl,
            @Value("classpath:promptTpl/writing_reference.md")
            Resource referencePromptTpl,
            CreditHandler credit,
            GPTRecordHandler record,
            ZhipuHandler zhipu) throws IOException {
        this.scorePromptTpl = scorePromptTpl.getContentAsString(Charset.defaultCharset());
        this.analysisPromptTpl = analysisPromptTpl.getContentAsString(Charset.defaultCharset());
        this.referencePromptTpl = referencePromptTpl.getContentAsString(Charset.defaultCharset());
        this.root = credit;
        credit.setNext(record);
        record.setNext(zhipu);
    }

    public WritingAnswer checkWriting(long uid, String question, String writing) {
        long now = new Date().getTime();
        WritingEntity writingEntity = this.writingDAO.save(WritingEntity.builder()
                .ctime(now).uid(now)
                        .status(WritingEntity.Status.INIT.ordinal())
                .question(question).writing(writing)
                .build());
        // 先保存一下记录
        Request scoreReq = Request.builder().biz(writingExamineBiz)
                .uid(uid)
                .bizId("score_" + writingEntity.getId().toString())
                .prompt(scorePromptTpl.formatted(question, writing)).build();
        Request analysisReq = Request.builder().biz(writingExamineBiz)
                .uid(uid)
                .bizId("analysis_" + writingEntity.getId().toString())
                .prompt(analysisPromptTpl.formatted(question, writing)).build();
        Request referenceReq = Request.builder().biz(writingExamineBiz)
                .uid(uid)
                .bizId("reference_" + writingEntity.getId().toString())
                .prompt(referencePromptTpl.formatted(question)).build();
        // 避免 NPE
        WritingEntity.Status status = WritingEntity.Status.INIT;
        try {
            Response scoreResp = root.handle(scoreReq);
            Response analysisResp = root.handle(analysisReq);
            Response referenceResp = root.handle(referenceReq);
            writingEntity.setScoreResult(scoreResp.getAnswer());
            writingEntity.setAnalysisResult(analysisResp.getAnswer());
            writingEntity.setReferenceResult(referenceResp.getAnswer());
            status = WritingEntity.Status.SUCCESS;
            return WritingAnswer.builder()
                    .score(scoreResp.getAnswer())
                    .analysis(analysisResp.getAnswer())
                    .reference(referenceResp.getAnswer())
                    .amount(scoreResp.getAmount()+ analysisResp.getAmount() + referenceResp.getAmount())
                    .tokens(scoreResp.getTokens() + analysisResp.getTokens() + referenceResp.getTokens())
                    .build();
        } catch (Exception e) {
            status = WritingEntity.Status.FAILED;
            throw e;
        }finally {
            writingEntity.setStatus(status.ordinal());
            this.writingDAO.save(writingEntity);
        }
    }

    // 转成 json 放进去 Response 中
    @lombok.Builder
    @Data
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
