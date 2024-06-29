package com.meoying.ai.ielts.utils;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Signature {

    public static String generateSignWithKey(Map<String, String> map, String key) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!"".equals(entry.getValue())) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        result = WechatPayMD5.md5Encode(result).toUpperCase();
        return result;
    }

    public String getSignWithKey(Map<String, String> map, String key) {
        return generateSignWithKey(map, key);
    }


    public boolean signVerifyWithKey(Map<String, String> map, String key) {
        String signFromAPIResponse = map.get("sign");
        if (!StringUtils.isNotBlank(signFromAPIResponse)) {
            log.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        log.info("服务器回包里面的签名是:" + signFromAPIResponse);
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = this.getSignWithKey(map, key);
        log.info("计算得到服务器回包里面的签名是:" + signForAPIResponse);
        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            // 签名验不过，表示这个API返回的数据有可能已经被篡改了
            log.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        log.info("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder prestr = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = (String) params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr.append(key + "=" + value);
            } else {
                prestr.append(key + "=" + value + "&");
            }
        }

        return prestr.toString();
    }

    public static String createLinkStringWithStrParams(Map<String, String> params) {

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder prestr = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr.append(key + "=" + value);
            } else {
                prestr.append(key + "=" + value + "&");
            }
        }

        return prestr.toString();
    }

}
