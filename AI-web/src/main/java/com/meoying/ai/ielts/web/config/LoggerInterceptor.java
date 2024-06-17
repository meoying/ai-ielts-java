package com.meoying.ai.ielts.web.config;

import com.alibaba.fastjson.JSONObject;
import com.meoying.ai.ielts.utils.JsonUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;


@Slf4j
@Component
public class LoggerInterceptor implements HandlerInterceptor {
    private static String REQ_BODY_MAX_LEN_KEY = "reqBodyMaxLen";
    private static String REQ_URL_MAX_LEN_KEY = "reqUrlMaxLen";
    private static String RES_BODY_MAX_LEN_KEY = "resBodyMaxLen";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        RequestLoggerInfo requestLoggerInfo = getRequestLoggerInfo(request);

        if(requestLoggerInfo.getReqBodyMaxLenValue() == 0 && requestLoggerInfo.getReqUrlMaxLenValue() == 0){
            return true;
        }

        log.info("{} 路径调用开始， 方法：{} , 入参： {}", requestLoggerInfo.getReqUrlMaxLenValue() == -1 ? uri : uri.substring(0,requestLoggerInfo.getReqUrlMaxLenValue()),
                handler, requestLoggerInfo.getReqBodyMaxLenValue() == -1 ? requestLoggerInfo.getRequestParametersString() : requestLoggerInfo.getRequestParametersString().substring(0,requestLoggerInfo.getReqBodyMaxLenValue()));

        return true;
    }

    private RequestLoggerInfo getRequestLoggerInfo(HttpServletRequest request){
        String uri = request.getRequestURI();
        String method = request.getMethod();
        RequestLoggerInfo requestLoggerInfo = RequestLoggerInfo.builder().build();
        if(HttpMethod.GET.name().equals(method) ){
            String reqBodyMaxLen = request.getParameter(REQ_BODY_MAX_LEN_KEY);
            String reqUrlMaxLen = request.getParameter(REQ_URL_MAX_LEN_KEY);
            try {
                int reqBodyMaxLenValue = StringUtils.isNotBlank(reqBodyMaxLen) ? Integer.valueOf(reqBodyMaxLen) : 0;
                int reqUrlMaxLenValue = StringUtils.isNotBlank(reqUrlMaxLen) ? Integer.valueOf(reqUrlMaxLen) : 0;
                reqUrlMaxLenValue = Math.min(reqUrlMaxLenValue, uri.length());
                reqBodyMaxLenValue = Math.min(reqBodyMaxLenValue,JsonUtils.toJson(request.getParameterMap()).length());
                requestLoggerInfo.setReqBodyMaxLenValue(reqBodyMaxLenValue);
                requestLoggerInfo.setReqUrlMaxLenValue(reqUrlMaxLenValue);
                requestLoggerInfo.setRequestParametersString(JsonUtils.toJson(request.getParameterMap()));
            }catch (Exception e){
                log.warn("parse reqBodyMaxLenValue fail!reqBodyMaxLen {}", request.getParameter(REQ_BODY_MAX_LEN_KEY));
            }
            return requestLoggerInfo;
        }
        String contentType = request.getContentType() == null ? "" : request.getContentType();
        // 如果是POST请求并且不是文件上传
        if (HttpMethod.POST.name().equals(method) && !contentType.equals(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            // 重新生成ServletRequest  这个新的 ServletRequest 获取流时会将流的数据重写进流里面
            String body = Strings.EMPTY;
            try {
                RequestWrapper requestWrapper = new RequestWrapper(request);
                body = requestWrapper.inputStream2String(requestWrapper.getInputStream());
            }catch (Exception e){
                log.warn("parse body fail!",e);
            }
            if(StringUtils.isNotBlank(body)){
                try {
                    JSONObject jsonObject = JSONObject.parseObject(body);
                    int reqBodyMaxLenValue = Objects.nonNull(jsonObject.get(REQ_BODY_MAX_LEN_KEY)) ? (int) jsonObject.get(REQ_BODY_MAX_LEN_KEY) : 0;
                    int reqUrlMaxLenValue =  Objects.nonNull(jsonObject.get(REQ_URL_MAX_LEN_KEY)) ? (int) jsonObject.get(REQ_URL_MAX_LEN_KEY) : 0;
                    reqUrlMaxLenValue = Math.min(reqUrlMaxLenValue, uri.length());
                    reqBodyMaxLenValue = Math.min(reqBodyMaxLenValue,body.length());
                    requestLoggerInfo.setReqBodyMaxLenValue(reqBodyMaxLenValue);
                    requestLoggerInfo.setReqUrlMaxLenValue(reqUrlMaxLenValue);
                    requestLoggerInfo.setRequestParametersString(body);
                }catch (Exception e){
                    log.warn("parse reqBodyMaxLenValue fail!url {}", e);
                }

            }
        }
        return  requestLoggerInfo;

    }

    @Data
    @Builder
    private static class RequestLoggerInfo{
        @Builder.Default
        private int reqBodyMaxLenValue = 0;
        @Builder.Default
        private int reqUrlMaxLenValue = 0;
        @Builder.Default
        private String requestParametersString = Strings.EMPTY;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestLoggerInfo requestLoggerInfo = getRequestLoggerInfo(request);

        if(requestLoggerInfo.getReqBodyMaxLenValue() == 0 && requestLoggerInfo.getReqUrlMaxLenValue() == 0){
            return;
        }
        String uri = request.getRequestURI();

        log.info("{} 路径调用结束， 方法：{} , 入参： {}, 出参 : {}", requestLoggerInfo.getReqUrlMaxLenValue() == -1 ? uri : uri.substring(0,requestLoggerInfo.getReqUrlMaxLenValue()),
                handler, requestLoggerInfo.getReqBodyMaxLenValue() == -1 ? requestLoggerInfo.getRequestParametersString() : requestLoggerInfo.getRequestParametersString().substring(0,requestLoggerInfo.getReqBodyMaxLenValue()),modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(Objects.nonNull(ex)){
            RequestLoggerInfo requestLoggerInfo = getRequestLoggerInfo(request);

            if(requestLoggerInfo.getReqBodyMaxLenValue() == 0 && requestLoggerInfo.getReqUrlMaxLenValue() == 0){
                return;
            }
            String uri = request.getRequestURI();
            log.info("{} 路径调用出现异常， 方法：{} , 入参： {}, 异常 : {}", requestLoggerInfo.getReqUrlMaxLenValue() == -1 ? uri : uri.substring(requestLoggerInfo.getReqUrlMaxLenValue()),
                    handler, requestLoggerInfo.getReqBodyMaxLenValue() == -1 ? requestLoggerInfo.getRequestParametersString() : requestLoggerInfo.getRequestParametersString().substring(requestLoggerInfo.getReqBodyMaxLenValue()),ex);

        }

    }
}
