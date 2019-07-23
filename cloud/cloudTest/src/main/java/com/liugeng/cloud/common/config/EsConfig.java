package com.liugeng.cloud.common.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackageClasses = ESClientSpringFactory.class)
public class EsConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Value("${elasticsearch.client.connectNum}")
    private Integer connectNum;

    @Value("${elasticsearch.client.connectPerRoute}")
    private Integer connectPerRoute;

    @Bean
    public HttpHost getHttpHost(){
        return new HttpHost(host,port,"http");
    }

    /**
    * 方法说明   Bean的初始化和销毁方法
    * @方法名    esClientSpringFactory
    * @参数      []
    * @返回值    com.liugeng.cloud.common.config.ESClientSpringFactory
    * @异常
    * @创建时间  2019/7/23 18:25
    * @创建人    liugeng
    */
    @Bean(initMethod = "init",destroyMethod = "close")
    public ESClientSpringFactory esClientSpringFactory(){
        return ESClientSpringFactory.build(getHttpHost(),username,password,connectNum,connectPerRoute);
    }

    /**
    * 方法说明   获取客户端
    * @方法名    getRClient
    * @参数      []
    * @返回值    org.elasticsearch.client.RestClient
    * @异常
    * @创建时间  2019/7/23 18:26
    * @创建人    liugeng
    */
    @Bean
    @Scope("singleton")
    public RestClient getRClient(){
        return esClientSpringFactory().getClient();
    }

    /**
    * 方法说明   获取客户端
    * @方法名    getRHLClient
    * @参数      []
    * @返回值    org.elasticsearch.client.RestHighLevelClient
    * @异常
    * @创建时间  2019/7/23 18:26
    * @创建人    liugeng
    */
    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRHLClient(){
        return esClientSpringFactory().getRhlClient();
    }
}
