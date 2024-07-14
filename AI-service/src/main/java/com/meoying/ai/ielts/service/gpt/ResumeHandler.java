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

    public ResumeHandler(List<Handler> handlerList) {
        this.handlerList = handlerList;
    }

    @Override
    public Response handle(Request req) {
        Response response = null;
        for (Handler handler : handlerList){
            try {
                return handler.handle(req);
            }catch (Exception e){
                log.error("handle fail!handler {}, req {}", JsonUtils.toJson(handler), JsonUtils.toJson(req), e);
            }
        }
        return null;
    }
}
