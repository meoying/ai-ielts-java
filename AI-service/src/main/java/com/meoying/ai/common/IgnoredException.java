package com.meoying.ai.common;

import lombok.Builder;

import java.util.Map;

public class IgnoredException extends RuntimeException implements CodeException, ErrorCode{
    private static final long serialVersionUID = -7613848122224829565L;
    private int code = -1;
    private Map<String, Object> data;
    private boolean logStack = true;


    public int getCode() {
        return this.code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public boolean isLogStack() {
        return this.logStack;
    }

    public void setLogStack(boolean logStack) {
        this.logStack = logStack;
    }

    public int getErrorCode() {
        return this.getCode();
    }

    public IgnoredException() {
    }

    public IgnoredException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public IgnoredException(int code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
    }
    public IgnoredException(int code, String msg, Map<String, Object> data) {
        super(msg);
        this.code = code;
        this.data = data;
    }
}
