package com.meoying.ai.ielts.service.pay;


import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class WeChatPayService {
    @Resource
    private Setting setting;

    private NativePayService nativePayService;

//    返回一个支付二维码
    public String prepay(PrepayRequest request) {
        PrepayResponse response = nativePayService.prepay(request);
        return response.getCodeUrl();
    }

    @PostConstruct
    public void init() {
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(setting.getMchId())
                        .privateKeyFromPath(setting.getKeyPath())
                        .merchantSerialNumber(setting.getMchSerialNum())
                        .apiV3Key(setting.getMchKey())
                        .build();
        // 构建service
        this.nativePayService = new NativePayService.Builder()
                .config(config).build();
    }
}
