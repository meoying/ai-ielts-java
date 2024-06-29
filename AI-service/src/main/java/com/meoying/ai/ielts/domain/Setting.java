package com.meoying.ai.ielts.domain;

import com.wechat.pay.java.core.RSAConfig;
import com.wechat.pay.java.core.util.PemUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Setting implements Serializable {
    /** 商户号 */
    private String merchantId = "1663021005";

    private String appId = "wxc6daad0e647d1544";

    private String signKey;
    /**
     * 数字证书路径
     * @return
     */
    private String certPath = "1663021005.p12";
    /**
     * 数字证书密码
     * @return
     */
    private String certPassword = "1663021005";

    private String payNotifyUrl;

    /** 商户API私钥路径 */
    public static String privateKeyPath = "5jwVhE3oyZFLH7RB1JrKvgxGtCW4pbXs";

    /** 商户证书序列号 */
    public static String merchantSerialNumber = "5E014BE2DBDC93104B19BA2245BB66DAEA92D96C";

    /** 商户APIV3密钥 */
    public static String apiV3Key ;

    RSAConfig rsaConfig =new RSAConfig.Builder().merchantId(getMerchantId()).privateKey(this.privateKeyPath)
            .merchantSerialNumber(this.merchantSerialNumber)
            .wechatPayCertificates(PemUtil.loadX509FromStream(
                    Setting.class.getResourceAsStream("/apiclient_cert.pem"))
            ).build();

    public RSAConfig getV3RSAConfig() {
        return rsaConfig;
    }
}
