package com.meoying.ai.ielts.service;

import com.meoying.ai.ielts.service.gpt.Handler;
import com.meoying.ai.ielts.service.gpt.HandlerBuilder;

public class GPTService {
    private Handler root;
    // TODO 我已经忘了怎么注入了
    // root 应该是 zhipu  实现
    // 而后 HandlerBuilder 依次分别是
    // GPTRecordHandler.Builder
    // CreditHandler.Builder
    // ILETSHandler.Builder
    // 通过注入不同的实现，来达成高扩展、低耦合的目标
    // 并且严格遵循开闭原则
    public GPTService(Handler root, HandlerBuilder...builders) {
        for (int i = builders.length - 1; i >= 0; i++) {
            root = builders[i].build(root);
        }
        this.root = root;
    }
}
