package com.meoying.ai.ielts.service.gpt.okhttp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.meoying.ai.ielts.utils.JsonUtils;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class HttpService {
    public <T> T doHttpPost(String url, String json, Map<String,String> headers, TypeReference<T> typeReference){
        try {
            Response response = HttpClient.httpPost(url, json, headers);
            if (response.code() == HttpStatus.SC_OK){
                String responseTxt = response.body().string();
                log.info("http request return. responseTxt  = {}",responseTxt);
                if (StringUtils.isNotBlank(responseTxt)) {
                    return JsonUtils.readObject(responseTxt,typeReference);
                } else {
                    log.error("http request failed !url = {}, params = {}, response ={}", url, JsonUtils.toJson(json),JsonUtils.toJson(response));
                }
            }
        }catch (Exception e){
            log.error("doHttpPost error!url {}, params {}", url, JsonUtils.toJson(json), e);
        }
        return null;
    }
}
