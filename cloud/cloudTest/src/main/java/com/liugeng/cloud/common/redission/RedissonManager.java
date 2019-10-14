package com.liugeng.cloud.common.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RedissonManager implements InitializingBean {

    @Autowired
    private Environment env;

    private Config config;

    private static RedissonClient redisson;

    @Override
    public void afterPropertiesSet() throws Exception {
        String hostName = env.getProperty("spring.redis.hostName");
        String password = env.getProperty("spring.redis.password");
        String port = env.getProperty("spring.redis.port");
        String[] nodes = hostName.split(",");
        config = new Config();
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        if (nodes.length > 1){
            for(int i=0;i<nodes.length;i++){
                nodes[i] = "redis://"+nodes[i];
            }
            // 这是用的集群server  // setScanInterval设置集群状态扫描时间
            config.useClusterServers()
                    .setScanInterval(2000)
                    .addNodeAddress(nodes)
                    .setPassword(password);
        } else{
            config.useSingleServer().setAddress("redis://" + hostName + ":" + port).setPassword(password);
        }
        redisson = Redisson.create(config);
    }

    public static RedissonClient getRedissonClient(){
        if (redisson == null){
            throw new RuntimeException("RedissonClient对象不存在");
        }
        return redisson;
    }
}
