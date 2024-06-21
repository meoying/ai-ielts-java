package com.meoying.ai.ielts.service.gpt.okhttp;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpClient {

    private static class DefaultOkHttpClientHolder{
        private final static OkHttpClient instance = new OkHttpClient();
    }

    public static OkHttpClient getInstance() {
        return DefaultOkHttpClientHolder.instance;
    }


    public static final MediaType JSON_TYPE = MediaType.get("application/json");

    public static Response httpPost(String url, String json) throws IOException{
        return doPost(url, json);
    }

    private static Response doPost(String url, String json) throws IOException{
        RequestBody body = RequestBody.create(json, JSON_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","Bearer f6f8e090a506b189b557ae10dc360462.gFDGuD9ZJLdfRuwP")
                .build();
        return getInstance().newCall(request).execute();
    }
}
