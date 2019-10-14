package com.liugeng.cloud.common.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissionManager {

    @Value("${spring.redis.address}")
    private  String address;

    @Value("${spring.redis.password}")
    private String password;

    public RedissonClient getRedissionClient(){
        String[] nodes = address.split(",");
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        for(int i=0;i<nodes.length;i++){
            nodes[i] = "redis://"+nodes[i];
        }
        RedissonClient redisson = null;
        Config config = new Config();
        // 这是用的集群server  // setScanInterval设置集群状态扫描时间
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(nodes)
                .setPassword(password);
        redisson = Redisson.create(config);

        //可通过打印redisson.getConfig().toJSON().toString()来检测是否配置成功
        return redisson;
    }
}
