package com.meoying.ai.ielts.service.gpt;

import com.meoying.ai.ielts.dao.GPTRecordDAO;
import com.meoying.ai.ielts.dao.entity.GPTRecordEntity;
import com.meoying.ai.ielts.utils.JsonUtils;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

// GPTRecordHandler
// 将 GPT 的调用结果记录到数据库里面
@Service
@Order
public class GPTRecordHandler implements Handler{
    @Resource
    private GPTRecordDAO gptRecordDAO;
    @Override
    public Response handle(Request req, HandlerContext context) {
        gptRecordDAO.save(GPTRecordEntity.builder().
                biz(req.getBiz()).
                uid(req.getUid()).
                request(JsonUtils.toJson(req)).
                response(Objects.isNull(context)  ? Strings.EMPTY : JsonUtils.toJson(context.getZhiPuResponse())).
                build());
        return null;
    }
}
