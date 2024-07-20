package com.meoying.ai.ielts.web.filter;

import com.meoying.ai.ielts.utils.JsonUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Data
@WebFilter(urlPatterns = "/*")
@Order(value = 2)
@Component
@ConfigurationProperties(prefix = "com.meoying.ai.ielts.web.filter.logger-filter")
public class LoggerFilter implements Filter {
    private int maxReqBodyLen;
    private int maxRespBodyLen;

    // 允许记录的最大的 URI 长度
    private int maxURLLen;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ReqInfo info = new ReqInfo();
        ContentCachingRequestWrapper httpReq = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper httpResp = new ContentCachingResponseWrapper((HttpServletResponse) response);
        try {
            info.method = httpReq.getMethod();
            String uri = httpReq.getRequestURI();
            info.uri = uri.substring(0, Math.min(this.maxURLLen, uri.length()));
            info.reqBody = this.readReqBodyIfNecessary(httpReq);
            chain.doFilter(httpReq, response);
        } catch (Exception e) {
            log.error("处理请求异常", e);
            throw e;
        } finally {
            try {
                info.respBody = this.readRespBodyIfNecessary(httpResp);
                httpResp.copyBodyToResponse();
                log.debug(JsonUtils.toJson(info));
            } catch (Exception e) {
                log.debug("打印请求失败", e);
            }

        }
    }

    private String readRespBodyIfNecessary(ContentCachingResponseWrapper resp) throws IOException {
        return this.readBody(resp.getContentAsByteArray(), this.maxRespBodyLen);
    }

    private String readReqBodyIfNecessary(ContentCachingRequestWrapper req) throws IOException {
        return this.readBody(req.getContentAsByteArray(), this.maxReqBodyLen);
    }

    private String readBody(byte[] body, int cfg) throws IOException {
        if(cfg == 0 || body == null ) {
            return null;
        }
        // 全部读取
        if(cfg < 0) {
            return new String(body);
        }
        int len = Math.min(body.length, cfg);
        return new String(body, 0, len);
    }

    @Data
    private static class ReqInfo {
        private String method;
        private String uri;
        private String reqBody;
        private String respBody;
    }
}
