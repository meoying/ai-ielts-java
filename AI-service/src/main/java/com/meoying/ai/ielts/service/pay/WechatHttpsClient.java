/**
 * @company: 网易云音乐科技有限公司.
 */

package com.meoying.ai.ielts.service.pay;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WechatHttpsClient {

    /** httpClient 最大连接数 */
    private static final int HTTP_CLIENT_MAX_TOTAL = 240;
    /** httpClient 每个路由最大连接数 */
    private static final int HTTP_CLIENT_MAX_PER_ROUTE = 230;
    /** 从连接池中获取连接的超时时间 ms */
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 500;
    /** 与服务器建立连接的超时时间 ms */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 2000;
    /** 从服务端获取响应数据的超时时间 ms */
    private static final int DEFAULT_SOCKET_TIMEOUT = 2500;
    /** 证书路径 - httpClient 映射关系表 */
    private static final Map<String,HttpClient> HTTP_CLIENT_MAP = new ConcurrentHashMap<>();
    /** 默认httpRequest配置 - 超时时间 等 */
    private static final RequestConfig DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
            .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT)
            .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
            .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
            .setRedirectsEnabled(false)
            .build();


    /**
     * 向微信服务器发起请求
     * @param url url
     * @param xmlStr xmlStr
     * @param certPath certPath
     * @param certPassWord certPassWord
     * @return String
     */
    public static String httpsPostWithCert(String url, String xmlStr, String certPath, String certPassWord){
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        // 传输xml
        httpPost.addHeader("Content-Type", "text/xml");
        // 使用UTF-8编码
        httpPost.setEntity(new StringEntity(xmlStr, "UTF-8"));
        // 长连接 请求服务端连接报活
        httpPost.addHeader("Connection", "keep-alive");
        httpPost.addHeader("Keep-Alive", "timeout=8");
        // 设置超时时间
        httpPost.setConfig(DEFAULT_REQUEST_CONFIG);

        // 获取httpClient
        HttpClient httpClient = HTTP_CLIENT_MAP.get(certPath);
        if(httpClient == null){
            httpClient = initCertHttpClient(certPath,certPassWord);
        }

        // 发送请求
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            log.info("与微信进行http交互发生异常");
            throw new RuntimeException("与微信进行http交互发生异常",e);
        }
    }

    /**
     * 获取 或 生成连接
     * @param certPath certPath
     * @param certPassWord certPassWord
     * @return HttpClient
     */
    private static synchronized HttpClient initCertHttpClient(String certPath, String certPassWord){
        // 查询httpClient是否已经存在,如果已经存在,则返回
        HttpClient httpClient = HTTP_CLIENT_MAP.get(certPath);
        if(httpClient != null){
            return httpClient;
        }

        // 创建新httpClient
        try {
            HttpClient certHttpClient = createCertHttpClient(certPath, certPassWord);
            HTTP_CLIENT_MAP.put(certPath,certHttpClient);
            return certHttpClient;
        } catch (Exception e) {
            throw new RuntimeException("创建httpClient失败:"+certPath,e);
        }
    }


    /**
     * 生成新httpsClient
     * @param certPath certPath
     * @param certPassWord certPassWord
     * @return HttpClient
     * @throws Exception Exception
     */
    private static HttpClient createCertHttpClient(String certPath, String certPassWord) throws Exception {

        // 获得密匙库
        KeyStore trustStore = KeyStore.getInstance("PKCS12");

        // 载入特定密钥库
        InputStream resourceAsStream = WechatHttpsClient.class.getClassLoader().getResourceAsStream(certPath);
        trustStore.load(resourceAsStream, certPassWord.toCharArray());
        resourceAsStream.close();

        // 获取 密钥管理工厂
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(trustStore, certPassWord.toCharArray());

        // 获取TLS ssl上下文
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(keyManagerFactory.getKeyManagers(), null, null);

        // 创建http 和 https 连接工厂
        SSLConnectionSocketFactory httpsSockFactory = new SSLConnectionSocketFactory(sslcontext);
        ConnectionSocketFactory httpSockFactory = PlainConnectionSocketFactory.getSocketFactory();

        // 连接 注册器
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http",httpSockFactory)
                .register("https", httpsSockFactory).build();


        // 线程池管理器
        PoolingHttpClientConnectionManager connectionManager
                = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(HTTP_CLIENT_MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(HTTP_CLIENT_MAX_PER_ROUTE);


        // 创建httpClient
        return HttpClients.custom()
                .setConnectionManagerShared(false)
                .setConnectionManager(connectionManager)
                .build();
    }






}
