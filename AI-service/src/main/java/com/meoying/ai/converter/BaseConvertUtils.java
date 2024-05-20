package com.meoying.ai.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.meoying.ai.Utils.JsonUtils;
import com.meoying.ai.service.dto.Mobile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BaseConvertUtils {
    public static <T> String list2String(List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return "";
        }
        return JsonUtils.toJson(list);
    }

    public static <T> List<T> string2List(String json, Class<T> clazz){
        if(StringUtils.isBlank(json)){
            return Lists.newArrayList();
        }
        return JsonUtils.readObject(json, new TypeReference<List<T>>() {
        });
    }

    public static Mobile convert2Mobile(String mobileString) {
        if(StringUtils.isBlank(mobileString)){
            return null;
        }
        return JsonUtils.readObject(mobileString, new TypeReference<Mobile>() {
        });
    }

    public static String convert2MobileString(Mobile mobile) {
        if(Objects.isNull(mobile)){
            return null;
        }
        return JsonUtils.toJson(mobile);
    }

}
