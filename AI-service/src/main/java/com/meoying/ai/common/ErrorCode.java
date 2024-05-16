package com.meoying.ai.common;

public interface ErrorCode {
    int getErrorCode();

    default int getCode() {
        return this.getErrorCode();
    }
}
