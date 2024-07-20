package com.meoying.ai.ielts.service.gpt;

import com.meoying.ai.ielts.utils.JsonUtils;
import com.meoying.ai.ielts.utils.WeightRoundRobinUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ResumeWeightHandler extends AbstractHandler {

    private List<Handler> handlerList;

    private WeightRoundRobinUtils WeightRoundRobinUtils;

    public ResumeWeightHandler(Map<Handler, Integer> handlerWeightMap) {
        this.handlerList = handlerWeightMap.keySet().stream().toList();
        WeightRoundRobinUtils = new WeightRoundRobinUtils(handlerWeightMap);
    }

    @Override
    public Response handle(Request req) {
        Handler handler = WeightRoundRobinUtils.chooseHandler();
        while(Objects.nonNull(handler)){
            try {
                Response response = handler.handle(req);
                WeightRoundRobinUtils.addWeight(handler, 1);
                return response;
            } catch (Exception e){
                WeightRoundRobinUtils.resetWeight(handler, 1);
                log.error("handle fail with exception!handler {}, req {}", JsonUtils.toJson(handler), JsonUtils.toJson(req), e);
            }
        }
        return null;
    }
}
