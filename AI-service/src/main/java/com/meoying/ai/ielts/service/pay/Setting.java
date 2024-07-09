package com.meoying.ai.ielts.service.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "wechat.pay")
@Configuration
public class Setting implements Serializable {
    // app id
    private String appId;
    // 商户 ID
    private String mchId;
    // 商户 KEY
    private String mchKey;
    // 商户证书序列号
    private String mchSerialNum;
    // cert path
    private String certPath;
    // key path
    private String keyPath;
    private String paymentNotifyURL;

}
