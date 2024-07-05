package com.meoying.ai.ielts.service.gpt;

import org.springframework.stereotype.Service;

// GPTService 本身也可以看做是一个 Handler 本体
@Service
public class GPTService implements Handler {
    private Handler root;
    public GPTService(Handler root, HandlerBuilder...builders) {
        for (int i = builders.length - 1; i >= 0; i++) {
            root = builders[i].build(root);
        }
        this.root = root;
    }
    @Override
    public Response handle(Request req, HandlerContext context) {
        return root.handle(req, context);
    }
}
