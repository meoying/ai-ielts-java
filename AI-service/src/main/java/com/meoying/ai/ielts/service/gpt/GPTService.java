package com.meoying.ai.ielts.service.gpt;

// GPTService 本身也可以看做是一个 Handler 本体
public class GPTService implements Handler{
    private Handler root;
    public GPTService(Handler root, HandlerBuilder...builders) {
        for (int i = builders.length - 1; i >= 0; i++) {
            root = builders[i].build(root);
        }
        this.root = root;
    }
    @Override
    public Response handle(Request req) {
        return root.handle(req);
    }
}
