package com.meoying.ai.ielts.service.gpt;

public class CreditInsufficientException extends RuntimeException{
    public CreditInsufficientException(long uid) {
        super("该用户积分不够: " + uid);
    }
}
