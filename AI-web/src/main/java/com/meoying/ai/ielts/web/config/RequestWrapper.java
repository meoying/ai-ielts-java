package com.meoying.ai.ielts.web.config;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;

@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    /**
     * 缓存下来的HTTP body
     */
    private byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    public ServletInputStream getInputStream() throws IOException {
        InputStream bodyStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bodyStream.read();
            }

            /**
             * 下面的方法一般情况下不会被使用
             * @return
             */
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        InputStream bodyStream = new ByteArrayInputStream(body);
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public byte[] getBody(){
        return body;
    }

    public String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            sb.append("get body params fail");
            log.error(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return sb.toString();
    }
}
