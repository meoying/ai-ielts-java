package com.meoying.ai.ielts.service.gpt;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.type.TypeReference;
import com.meoying.ai.ielts.service.gpt.okhttp.HttpService;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import com.zhipu.oapi.service.v4.model.ModelData;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

// 实现这个方法
// 编写单元测试，确保能够调通
// 是用 resource 里面的 prompt 作为 prompt
@Service
@ConfigurationProperties(prefix = "zhipu")
@Slf4j
public class ZhipuHandler extends AbstractHandler {
    @Getter
    @Setter
    private String apiKey;
    @Getter
    @Setter
    private String model;
    @Getter
    @Setter
    private long price;

    private ClientV4 client;


    @PostConstruct
    public void init() {
        this.client = new ClientV4.Builder(apiKey)
                .enableTokenCache()
                .networkConfig(600, 10, 10, 10, TimeUnit.SECONDS)
                .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
                .build();
    }
    @Override
    public Response handle(Request req) {
        ModelApiResponse resp = client.invokeModelApi(ChatCompletionRequest.builder()
                .model(this.model)
                .requestId(String.format("%s_%s", req.getBiz(), req.getBizId()))
                .messages(List.of(new ChatMessage("user", req.getPrompt())))
                        .stream(false)
                        .invokeMethod(Constants.invokeMethod)
                .maxTokens(4095)
                .build());
        String answer = "";
        ModelData message = resp.getData();
        log.debug("zhipu响应：", resp);
        if(message == null) {
            throw new RuntimeException("调用 zhipu 失败，未能获得数据");
        }
        if(Objects.nonNull(message) && !CollectionUtils.isEmpty(message.getChoices()) && Objects.nonNull(message.getChoices().get(0)) && Objects.nonNull(message.getChoices().get(0).getMessage())){
            answer = message.getChoices().get(0).getMessage().getContent().toString();
        }
        int totalTokens = message.getUsage().getTotalTokens();
        // 先乘后除
        long amount = Math.ceilDiv(totalTokens * price, 1000);
        return Response.builder()
                .answer(answer)
                .tokens(totalTokens)
                .amount(amount)
                .build();
    }
}
