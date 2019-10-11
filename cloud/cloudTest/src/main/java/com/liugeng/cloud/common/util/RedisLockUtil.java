package com.liugeng.cloud.common.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLockUtil {

    @Autowired
    private RedisTemplate<String,Long> redisTemplate;

    /**
    * 方法说明   获取分布式锁
    * @方法名    tryGetDistributedLock
    * @参数      [key, value]
    * @返回值    boolean
    * @异常      
    * @创建时间  2019/10/11 10:51
    * @创建人    liugeng
    */
    public boolean tryGetDistributedLock(String key,Long value){
        // redis不存在则表示无竞争，设置key值
        if (redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        // 存在key表示有竞争，获取key的值
        Long curVal = redisTemplate.opsForValue().get(key);
        // 超时了才可抢占key
        if (null != curVal && curVal < System.currentTimeMillis()){
            // 先setkey的值获取到锁
            Long oldVal = redisTemplate.opsForValue().getAndSet(key,value);
            // 相等的值表示来自同客户端
            if (oldVal != null && oldVal.equals(curVal)){
                return true;
            }
        }
        // 如果是可重入，需在此判断未超时进行累加，并在解锁的时候进行累减
        return false;
    }

    /**
    * 方法说明   释放分布式锁
    * @方法名    unLock
    * @参数      [key, value]
    * @返回值    boolean
    * @异常      
    * @创建时间  2019/10/11 10:52
    * @创建人    liugeng
    */
    public boolean unLock(String key,Long value){
        // 获取key的值
        Long oldVal = redisTemplate.opsForValue().get(key);
        // 将key的值与传入的对象值比较
        if (null != oldVal && oldVal.equals(value)){
            // 保持一致的对象值才能删除key释放锁
            return redisTemplate.opsForValue().getOperations().delete(key);
        }
        return false;
    }


}
