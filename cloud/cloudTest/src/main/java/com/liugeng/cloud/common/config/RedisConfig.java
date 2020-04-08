package com.liugeng.cloud.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
public class RedisConfig{

    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);


    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return jedisPoolConfig;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        JedisPoolConfig jedisPoolConfig = getJedisPoolConfig();
        connectionFactory.setPoolConfig(jedisPoolConfig);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<?,?>  redisTemplate(){
        RedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        RedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory());
        redisTemplate.setDefaultSerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return  redisTemplate;
    }


    @Bean
    public JedisPool getRedisPool(){
        JedisPoolConfig jedisPoolConfig = getJedisPoolConfig();
        JedisConnectionFactory connectionFactory = redisConnectionFactory();
        logger.info("***************getHostName: " + connectionFactory.getHostName()) ;
        logger.info("***************getPort: " + connectionFactory.getPort()) ;
        logger.info("***************getTimeout: " + connectionFactory.getTimeout()) ;
        logger.info("***************getPassword: " + connectionFactory.getPassword()) ;
        logger.info("***************getDatabase: " + connectionFactory.getDatabase()) ;
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,connectionFactory.getHostName(),connectionFactory.getPort(),
                connectionFactory.getTimeout(),connectionFactory.getPassword(),connectionFactory.getDatabase());
        return jedisPool;
    }
}
