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

    @Value("${elasticsearch.client.connectNum}")
    private Integer connectNum;

    @Value("${elasticsearch.client.connectPerRoute}")
    private Integer connectPerRoute;

    @Bean
    public HttpHost getHttpHost(){
        return new HttpHost(host,port,"http");
    }

    @Bean(initMethod = "init",destroyMethod = "close")
    public ESClientSpringFactory esClientSpringFactory(){
        return ESClientSpringFactory.build(getHttpHost(),connectNum,connectPerRoute);
    }

    @Bean
    @Scope("singleton")
    public RestClient getRClient(){
        return esClientSpringFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRHLClient(){
        return esClientSpringFactory().getRhlClient();
    }
}
