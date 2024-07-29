package com.meoying.ai.ielts.service.gpt.okhttp;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class HttpClient {

    private static class DefaultOkHttpClientHolder{
        private final static OkHttpClient instance = new OkHttpClient();
    }

    public static OkHttpClient getInstance() {
        return DefaultOkHttpClientHolder.instance;
    }


    public static final MediaType JSON_TYPE = MediaType.get("application/json");

    public static Response httpPost(String url, String json,Map<String,String> headerMap) throws IOException{
        return doPost(url, json, headerMap);
    }

    private static Response doPost(String url, String json,Map<String,String> headerMap) throws IOException{
        RequestBody body = RequestBody.create(json, JSON_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .headers(buildHeaders(headerMap))
                .build();
        return getInstance().newCall(request).execute();
    }

    private static Headers buildHeaders(Map<String,String> headerMap){
        Headers headers = null;
        Headers.Builder headerBuilder = new Headers.Builder();
        if(Objects.nonNull(headerMap)){
            headerMap.entrySet().stream().forEach(entry->{
                headerBuilder.add(entry.getKey(), entry.getValue());
            });
        }
        headers = headerBuilder.build();
        return headers;
    }
}
