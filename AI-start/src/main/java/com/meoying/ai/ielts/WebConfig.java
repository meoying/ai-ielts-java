package com.meoying.ai.ielts;

import com.meoying.ai.ielts.web.filter.LoggerFilter;
import com.meoying.ai.ielts.web.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8000");
    }

//    @Bean
//    public FilterRegistrationBean<CommonsRequestLoggingFilter> logFilter() {
//        FilterRegistrationBean<CommonsRequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new CommonsRequestLoggingFilter());
//        registrationBean.addUrlPatterns("/*"); // Specify URL patterns to filter
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean<LoginFilter> loginFilter() {
//        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new LoginFilter());
//        registrationBean.addUrlPatterns("/*");// Specify URL patterns to filter
//        registrationBean.setOrder(2);
//        return registrationBean;
//    }
}
