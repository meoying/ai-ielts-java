package com.meoying.ai.ielts.service.gpt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    // 提示，是合并了用户输入之后的完整的提示
    private String prompt;
    //场景，biz + biz_id 必须唯一
    private String biz;
    // biz id
    private String bizId;
    //用户id
    private long uid;
}
