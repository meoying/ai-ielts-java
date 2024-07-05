package com.meoying.ai.ielts.service.gpt;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.type.TypeReference;
import com.meoying.ai.ielts.service.gpt.okhttp.HttpService;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

// 实现这个方法
// 编写单元测试，确保能够调通
// 是用 resource 里面的 prompt 作为 prompt
@Service
public class ZhipuHandler implements Handler{
    @Autowired
    private HttpService httpService;

    // TODO 做成配置的，从配置文件里面读取
    private long price = 10;
    @Override
    public Response handle(Request req) {
        ZhipuInfo zhipu = ZhipuInfo.builder()
                .content(req.getPrompt())
                .role("user")
                .build();
        ZhipuRequest zhipuRequest = ZhipuRequest.builder()
                .messages(Arrays.asList(zhipu))
                .model("glm-4")
                .build();
        String json = JSON.toJSONString(zhipuRequest);
        ZhipuResponse message = httpService.doHttpPost("https://open.bigmodel.cn/api/paas/v4/chat/completions", json, new TypeReference<ZhipuResponse>() {});
        String answer = "";
        if(Objects.nonNull(message) && !CollectionUtils.isEmpty(message.getChoices()) && Objects.nonNull(message.getChoices().get(0)) && Objects.nonNull(message.getChoices().get(0).message)){
            answer = message.getChoices().get(0).message.content;
        }
        // 先乘后除
        long amount = Math.ceilDiv(message.usage.totalTokens * price, 1000);
        return Response.builder()
                .answer(answer)
                .tokens(message.usage.totalTokens)
                .amount(amount)
                .build();
    }


    @Data
    @Builder
    private static class ZhipuRequest{
        private String model;
        private List<ZhipuInfo> messages;
    }
    @Data
    @Builder
    private static class ZhipuInfo{
        private String role;
        private String content;
    }

    @Data
    private static class ZhipuResponse{
        private List<Choices> choices;
        private long created;
        private String id;
        private String model;
        private String request_id;
        private Usage usage;
    }

    @Data
    private static class Choices{
        private String finish_reason;
        private int index;
        private Message message;
    }

    @Data
    private static class Usage {
        @JsonAlias("total_tokens")
        private int totalTokens;
    }

    @Data
    private static class Message{
        private String content;
        private String role;
    }
}
