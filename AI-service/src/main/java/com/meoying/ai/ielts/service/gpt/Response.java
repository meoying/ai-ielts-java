package com.meoying.ai.ielts.service.gpt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String answer;
    // tokens 数量
    private int tokens;
}
