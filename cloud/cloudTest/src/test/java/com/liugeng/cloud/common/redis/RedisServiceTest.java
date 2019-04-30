package com.liugeng.cloud.common.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisServiceTest{

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void testRedis(){
        jedisPool.getResource().set("123","123");
        //String leiTest = redisService.get("leiTest");
        String S = jedisPool.getResource().get("123");
        //int A账号 = redisService.get("A账号");
        //System.out.println(leiTest);
        System.out.println(S);
        //System.out.println(A账号);
    }

}