package com.meoying.ai.ielts.service.gpt;

import lombok.Data;

@Data
public class Request {
    // 提示
    private String prompt;
    // 用户输入
    private String input;
}
