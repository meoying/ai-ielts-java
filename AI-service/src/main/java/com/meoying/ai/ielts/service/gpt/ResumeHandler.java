package com.meoying.ai.ielts.service.gpt;

import com.meoying.ai.ielts.utils.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ResumeHandler extends AbstractHandler{

    private List<Handler> handlerList;

    @Resource
    private RetryHandler retryHandler;

    public ResumeHandler(ZhipuHandler zhipu, GPTRecordHandler gptRecordHandler) {
        zhipu.setNext(gptRecordHandler);
        handlerList.add(zhipu);
        handlerList.add(gptRecordHandler);
    }

    @Override
    public Response handle(Request req) {
        Response response = null;
        for (Handler handler : handlerList){
            try {
                response = handler.handle(req);
            }catch (Exception e){
                log.error("handle fail!handler {}, req {}", JsonUtils.toJson(handler), JsonUtils.toJson(req), e);
            }
        }
        return null;
    }
}
