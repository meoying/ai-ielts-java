package com.meoying.ai.ielts.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result(String msg) {
        this.setMsg(msg);
    }
}
