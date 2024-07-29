package com.meoying.ai.ielts.utils;
import com.meoying.ai.ielts.service.gpt.Handler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeightRoundRobinUtils {

    private List<Handler> handlers = new ArrayList<>();
    private Map<Handler, Integer> weightMap = new HashMap<>();
    private int currentWeightIndex = -1;

    public WeightRoundRobinUtils(Map<Handler, Integer> handlerMap) {
        this.handlers.addAll(handlerMap.keySet());
        for (Handler handler : handlerMap.keySet()) {
            int weight = weightMap.get(handler);
            weightMap.put(handler, weight);
        }
    }

    public void addWeight(Handler handler, Integer weight){
        Integer value = weightMap.getOrDefault(handler, 1);
        weightMap.put(handler, value + weight);
    }

    public void resetWeight(Handler handler, Integer weight){
        weightMap.put(handler, weight);
    }

    public void divideWeight(Handler handler, Integer multiple){
        Integer value = weightMap.getOrDefault(handler, 0);
        weightMap.put(handler, value/multiple);
    }

    public synchronized Handler chooseHandler() {
        int weightSum = weightMap.values().stream().reduce(Integer::sum).orElse(0);
        while (true) {
            currentWeightIndex = (currentWeightIndex + 1) % handlers.size();
            Handler handler = handlers.get(currentWeightIndex);
            int weight = weightMap.get(handler);
            if (weight >= weightSum) {
                return handler;
            }
            weightSum -= weight;
        }
    }
}
