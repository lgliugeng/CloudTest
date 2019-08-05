package com.liugeng.cloud.common.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ESClientSpringFactory {

    private Logger logger = LoggerFactory.getLogger(ESClientSpringFactory.class);

    public static int CONNECT_TIMEOUT_MILLIS = 1000;
    public static int SOCKET_TIMEOUT_MILLIS = 30000;
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;
    public static int MAX_CONN_PER_ROUTE = 10;
    public static int MAX_CONN_TOTAL = 30;
    public static int MAX_CONN_THREAD = 5;

    private static HttpHost HTTP_HOST;
    private static String USERNAME;
    private static String PASSWORD;
    private RestClientBuilder builder;
    private RestClient restClient;
    private RestHighLevelClient restHighLevelClient;
    private final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    private IOReactorConfig ioReactorConfig;

    private static ESClientSpringFactory esClientSpringFactory = new ESClientSpringFactory();

    private ESClientSpringFactory(){}

    /**
    * 方法说明   初始化值
    * @方法名    build
    * @参数      [httpHost, username, password, maxConnectNum, maxConnectPerRoute]
    * @返回值    com.liugeng.cloud.common.config.ESClientSpringFactory
    * @异常
    * @创建时间  2019/7/23 18:31
    * @创建人    liugeng
    */
    public static ESClientSpringFactory build(HttpHost httpHost,String username,String password,
                                              Integer maxConnectNum, Integer maxConnectPerRoute){
        HTTP_HOST = httpHost;
        USERNAME = username;
        PASSWORD = password;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        return  esClientSpringFactory;
    }

    /**
    * 方法说明   初始化值
    * @方法名    build
    * @参数      [httpHost, username, password, connectTimeOut, socketTimeOut, connectionRequestTime, maxConnectNum, maxConnectPerRoute]
    * @返回值    com.liugeng.cloud.common.config.ESClientSpringFactory
    * @异常
    * @创建时间  2019/7/23 18:32
    * @创建人    liugeng
    */
    public static ESClientSpringFactory build(HttpHost httpHost,String username,String password,
                                              Integer connectTimeOut, Integer socketTimeOut,Integer connectionRequestTime,
                                              Integer maxConnectNum, Integer maxConnectPerRoute){
        HTTP_HOST = httpHost;
        USERNAME = username;
        PASSWORD = password;
        CONNECT_TIMEOUT_MILLIS = connectTimeOut;
        SOCKET_TIMEOUT_MILLIS = socketTimeOut;
        CONNECTION_REQUEST_TIMEOUT_MILLIS = connectionRequestTime;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        return  esClientSpringFactory;
    }

    //初始化实例
    public void init(){
        builder = RestClient.builder(HTTP_HOST);
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(USERNAME, PASSWORD));  //es账号密码（默认用户名为elastic）
        ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(MAX_CONN_THREAD).build();//设置线程数
        setRequestConfigCallbackConfig();
        setClientConfigCallbackConfig();
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);
        logger.info("init factory");
    }

    // 配置连接时间延时
    public void setRequestConfigCallbackConfig(){
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);//设置超时时间
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
    }
    // 使用异步httpclient时设置并发连接数和认证
    public void setClientConfigCallbackConfig(){
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);//设置连接数
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            httpClientBuilder.setDefaultIOReactorConfig(ioReactorConfig);
            //httpClientBuilder.disableAuthCaching(); //禁用 preemptive 身份验证，每次都不发送身份验证，收到401时再重新发送
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);//设置认证
            return httpClientBuilder;
        });
    }

    //修改节点请求（默认向所有节点请求）
    public void setNodeSelector(){
        builder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);//与任何具有元数据且没有master角色或具有数据data 角色的节点匹配的选择器
    }

    //设置监听器监听故障节点并进行处理
    public void setFailureListen(){
        builder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                super.onFailure(node);
                //TODO
                logger.warn(node.getName() + "故障");
            }
        });
    }

    public RestClient getClient(){
        return restClient;
    }

    public RestHighLevelClient getRhlClient(){
        return restHighLevelClient;
    }

    //销毁
    public void close() {
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("close client");
    }
}
