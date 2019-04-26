package com.liugeng.cloud.common;

import com.liugeng.cloud.common.interceptor.ShortUrlInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public ShortUrlInterceptor getShortUrlInterceptor(){
        return new ShortUrlInterceptor();
    }

    /**
    * 方法说明   拦截注入
    * @方法名    addInterceptors
    * @参数      [registry]
    * @返回值    void
    * @异常
    * @创建时间  2019/4/26 17:28
    * @创建人    liugeng
    */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getShortUrlInterceptor()).addPathPatterns("/**").excludePathPatterns("/**/index/**","/**/cloud/test/dataToPdf/**","/**/error/**","/error.html","/**/cloudS/**");
    }
}
