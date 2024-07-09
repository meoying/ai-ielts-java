package com.meoying.ai.ielts.service.gpt;

import com.meoying.ai.ielts.dao.GPTRecordDAO;
import com.meoying.ai.ielts.dao.entity.GPTRecordEntity;
import com.meoying.ai.ielts.utils.JsonUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

// GPTRecordHandler
// 将 GPT 的调用结果记录到数据库里面
@Service
public class GPTRecordHandler implements Handler{
    @Resource
    private GPTRecordDAO gptRecordDAO;

    private Handler next;
    @Override
    public Response handle(Request req) {
        Response resp = this.next.handle(req);
        gptRecordDAO.save(GPTRecordEntity.builder().
                uid(req.getUid()).
                biz(req.getBiz()).
                request(JsonUtils.toJson(req)).
                response(JsonUtils.toJson(resp)).
                build()
        );
        return resp;
    }

    public static class Builder implements HandlerBuilder {

        @Override
        public Handler build(Handler next) {
            return null;
        }
    }
}
