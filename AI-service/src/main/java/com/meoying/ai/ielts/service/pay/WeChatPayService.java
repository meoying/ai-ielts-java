package com.meoying.ai.ielts.service.pay;


import com.meoying.ai.ielts.domain.Setting;
import com.meoying.ai.ielts.domain.WechatParamDto;
import com.meoying.ai.ielts.utils.JsonUtils;
import com.meoying.ai.ielts.utils.Signature;
import com.meoying.ai.ielts.utils.XMLUtil;
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
    private Signature signature;

    public static final String UNIFIED_ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String PATTERN_SIMPLE_DATETIME = "yyyyMMddHHmmss";
    /** 支付URL默认过期时间 */
    private static int DEFAULT_URL_EXPIRE_MIN = 30;

    /**
     *
     * @param paymentSerialNo   支付流水单号
     * @param relPaymentItemNo  关联支付明细号
     * @param amount
     * @param urlExpireMin
     * @return
     */
    public Object qrHandle(String paymentSerialNo, String relPaymentItemNo,BigDecimal amount, String urlExpireMin){
        WechatParamDto wechatParamDto = new WechatParamDto("NATIVE",
                this.getTime(PATTERN_SIMPLE_DATETIME), relPaymentItemNo,
                paymentSerialNo, "数字商品", getCentValue(amount), StringUtils.EMPTY,getUrlExpireMin(urlExpireMin));
        Setting setting = new Setting();
        String response = "";
        Map<String, String> params = buildWechatParam(wechatParamDto, setting.getPayNotifyUrl());
        try {
            response = this.doGetPayUrl(params, setting);
        }catch (Exception e){
            log.warn("handle fail!paymentSerialNo {}, amount {}, urlExpireMin {}, setting {}",paymentSerialNo, amount, urlExpireMin, JsonUtils.toJson(setting), e);
        }

        if (StringUtils.isEmpty(response)) {
            log.warn("微信APP支付接口返回为空");
            return null;
        }

        try {
            Map<String, String> responseMap = XMLUtil.getMapFromXML(response);
            Map<String, String> paramsMap = new HashMap<>();
            if (responseMap != null && "SUCCESS".equals(responseMap.get("return_code"))
                    && "SUCCESS".equals(responseMap.get("result_code"))) {
                // 拼装结果
                Long timestamp = System.currentTimeMillis() / 1000;
                paramsMap.put("appid", setting.getAppId());
                paramsMap.put("partnerid", setting.getMerchantId());
                paramsMap.put("prepayid", responseMap.get("prepay_id"));
                paramsMap.put("package", "Sign=WXPay");
                paramsMap.put("noncestr", UUID.randomUUID().toString().replace("-", ""));
                paramsMap.put("timestamp", timestamp.toString());
                String sign = signature.getSignWithKey(paramsMap, setting.getSignKey());
                paramsMap.put("sign", sign);
                log.info("构造微信APP支付响应结果: {}", JsonUtils.toJson(params));
                return paramsMap;
            } else if (responseMap != null) {
                String returnCode = responseMap.get("return_code");
                String resultCode = responseMap.get("result_code");
                log.error("发送微信支付请求失败，returnCode={}, resultCode={}", returnCode, resultCode);
            }
        } catch (Exception e) {
            log.error("处理微信APP支付响应结果异常", e);
        }
        return null;
    }

    public static Map<String, String> buildWechatParam(WechatParamDto baseWechatPayMeta, String notifyUrl) {
        Map<String, String> params = new HashMap<>();
        params.put("device_info", baseWechatPayMeta.getDeviceInfo());
        params.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        params.put("body", baseWechatPayMeta.getBody());
        params.put("detail", baseWechatPayMeta.getDetail());
        params.put("attach", baseWechatPayMeta.getAttach());
        params.put("out_trade_no", baseWechatPayMeta.getOutTradeNo());
        params.put("fee_type", baseWechatPayMeta.getFeeType());
        params.put("total_fee", baseWechatPayMeta.getTotalFee());
        params.put("spbill_create_ip", baseWechatPayMeta.getSpbillCreateIp());
        params.put("time_start", baseWechatPayMeta.getTimeStart());
        params.put("time_expire", baseWechatPayMeta.getTimeExpire());
        params.put("goods_tag", baseWechatPayMeta.getGoodsTag());
        params.put("notify_url", notifyUrl);
        params.put("trade_type", baseWechatPayMeta.getTradeType());
        params.put("product_id", baseWechatPayMeta.getProductId());
        params.put("limit_pay", baseWechatPayMeta.getLimitPay());
        params.put("openid", baseWechatPayMeta.getOpenid());
        return params;
    }

    private String doGetPayUrl(Map<String, String> params, Setting wechatSetting) throws UnsupportedEncodingException {
        params.put("appid", wechatSetting.getAppId());
        params.put("mch_id", wechatSetting.getMerchantId());
        String sign = Signature.generateSignWithKey(params, wechatSetting.getSignKey());
        params.put("sign", sign);
        String requestData = XMLUtil.mapToXmlStr(params);
        log.info("微信支付请求: {}", requestData);
        String response = WechatHttpsClient.httpsPostWithCert(UNIFIED_ORDER_API, requestData, wechatSetting.getCertPath(), wechatSetting.getCertPassword());
        log.info("微信支付响应: {}", response);
        return response;
    }

    /**
     * 获取支付URL过期时间
     * @param urlExpireMin
     * @return int
     */
    private static int getUrlExpireMin(String urlExpireMin){
        int ret = DEFAULT_URL_EXPIRE_MIN;
        if(StringUtils.isNumeric(urlExpireMin)){
            int intValue = Integer.parseInt(urlExpireMin);
            if(intValue >= 1){
                ret = intValue;
            }
        }
        return ret;
    }

    private String getTime(String pattern) {
        return (new SimpleDateFormat(pattern)).format(Calendar.getInstance().getTime());
    }

    public String getCentValue(BigDecimal value) {
        return String.valueOf(value.movePointRight(2).longValue());
    }

}
