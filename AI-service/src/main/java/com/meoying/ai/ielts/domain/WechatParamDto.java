package com.meoying.ai.ielts.domain;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class WechatParamDto {
    private String spbillCreateIp = "127.0.0.1"; // 直接用一个写死的ip，这类信息无需传真实给wechat
    private String tradeType; //
    private String deviceInfo = ""; // 设备信息先不传
    private String attach = "";
    private String feeType = "CNY"; // 默认人人民币支付
    private String timeStart;
    private String timeExpire = "";
    private String goodsTag = "";
    private String productId = "";
    private String limitPay = "";
    private String openid = "";
    private String outTradeNo;
    private String body = "";
    private String totalFee;
    private String detail;
    private String mchId;

    /**
     *
     * @param tradeType
     * @param timeStart
     * @param productId
     * @param outTradeNo
     * @param body
     * @param totalFee
     * @param detail
     */
    public WechatParamDto(String tradeType, String timeStart, String productId, String outTradeNo, String body,
                          String totalFee, String detail,int timeExpireMin) {
        this.tradeType = tradeType;
        this.timeStart = timeStart;
        this.productId = productId;
        this.outTradeNo = outTradeNo;
        this.body = body;
        this.totalFee = totalFee;
        this.detail = detail;
        this.timeExpire = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(DateUtils.addMinutes(new Date(),timeExpireMin));
    }

}
