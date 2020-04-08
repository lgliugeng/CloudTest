package com.liugeng.cloud.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class RedisClusterConfig {

    /**集群节点*/
    @Value("#{'${spring.redis.cluster.nodes}'.split(',')}")
    private List<String> nodes;

    @Bean("jedisPoolConfigCluster")
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMaxTotal(2000);
        jedisPoolConfig.setMinIdle(50);
        jedisPoolConfig.setMaxWaitMillis(1000);
        return jedisPoolConfig;
    }

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        Set<RedisNode> redisNodes = new HashSet<>();
        nodes.forEach(s -> {
            redisNodes.add(new RedisNode(s.split(":")[0],Integer.parseInt(s.split(":")[1])));
        });
        redisClusterConfiguration.setClusterNodes(redisNodes);
        redisClusterConfiguration.setMaxRedirects(6);
        return redisClusterConfiguration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactoryCluster() {
        LettucePoolingClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(getJedisPoolConfig())
                .build();
        RedisConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration(),lettuceClientConfiguration);
        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate clusterRedisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactoryCluster());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
