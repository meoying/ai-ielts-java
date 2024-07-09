package com.meoying.ai.ielts.service.gpt;

import com.meoying.ai.ielts.dao.GPTRecordDAO;
import com.meoying.ai.ielts.dao.entity.GPTRecordEntity;
import com.meoying.ai.ielts.utils.JsonUtils;
import jakarta.annotation.Resource;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;

// GPTRecordHandler
// 将 GPT 的调用结果记录到数据库里面
@Service
public class GPTRecordHandler extends AbstractHandler {
    @Resource
    private GPTRecordDAO gptRecordDAO;

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
}
