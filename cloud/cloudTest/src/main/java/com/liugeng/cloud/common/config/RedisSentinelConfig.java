package com.liugeng.cloud.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class RedisSentinelConfig {

    /**哨兵节点*/
    @Value("#{'${spring.redis.sentinel.nodes}'.split(',')}")
    private List<String> nodes;

    @Bean("jedisPoolConfigSentinel")
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMaxTotal(2000);
        jedisPoolConfig.setMinIdle(50);
        jedisPoolConfig.setMaxWaitMillis(1000);
        return jedisPoolConfig;
    }

    @Bean("redisSentinelConfiguration")
    public RedisSentinelConfiguration sentinelConfiguration() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.master("mymaster");
        Set<RedisNode> redisNodes = new HashSet<>();
        nodes.forEach(s -> {
            redisNodes.add(new RedisNode(s.split(":")[0],Integer.parseInt(s.split(":")[1])));
        });
        redisSentinelConfiguration.setSentinels(redisNodes);
        return redisSentinelConfiguration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactorySentinel() {
        LettucePoolingClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(getJedisPoolConfig())
                .build();
        RedisConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(sentinelConfiguration(),lettuceClientConfiguration);
        return redisConnectionFactory;
    }

    /*@Bean
    public JedisSentinelPool jedisSentinelPool(){
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", new HashSet<>(nodes),getJedisPoolConfig());
        return jedisSentinelPool;
    }

    @Bean
    public JedisPool jedisPool() {
        JedisPool jedisPool = new JedisPool(getJedisPoolConfig(),jedisSentinelPool().getCurrentHostMaster().getHost(),
                jedisSentinelPool().getCurrentHostMaster().getPort(),0);
        return jedisPool;
    }*/

    @Bean
    public RedisTemplate sentinelRedisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactorySentinel());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
