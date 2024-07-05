package com.meoying.ai.ielts.service.gpt;

// GPTRecordHandler
// 将 GPT 的调用结果记录到数据库里面
public class GPTRecordHandler implements Handler{
    private Handler next;
    @Override
    public Response handle(Request req) {
        Response resp = this.next.handle(req);
        return null;
    }

    public static class Builder implements HandlerBuilder {

        @Override
        public Handler build(Handler next) {
            return null;
        }
    }
}
