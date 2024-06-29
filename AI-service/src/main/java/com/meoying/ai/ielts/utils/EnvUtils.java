package com.meoying.ai.ielts.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class EnvUtils {
    static {
        Properties envProperties = new Properties();
        try {
            envProperties.load(Resources.getResourceAsReader("env.properties"));
            envProperties.stringPropertyNames().stream().forEach(x->{
                System.getProperties().setProperty(x,envProperties.getProperty(x));
            });
        } catch (IOException e) {
            log.error("parse error!",e);
        }
    }
}
