package com.meoying.ai.ielts.service.gpt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
    // 提示
    private String prompt;
    // 用户输入
    private String input;
    //场景
    private String biz;
    //用户id
    private long uid;
}
