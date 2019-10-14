package com.liugeng.cloud.common.redission;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class RedissonDistributedLocker {

    //RedissonClient已经由配置类生成，这里自动装配即可
    private static RedissonClient redissonClient = RedissonManager.getRedissonClient();

    //leaseTime为加锁时间，单位为秒
    public static RLock lock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    //timeout为加锁时间，时间单位由unit确定
    public static RLock lock(String lockKey, TimeUnit unit , long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }
    //tryLock()，马上返回，拿到lock就返回true，不然返回false。
    //带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false.
    public static boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    // 解锁
    public static void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    // 解锁
    public static void unlock(RLock lock) {
        lock.unlock();
    }

    // 强制解锁
    public static void focusunlock(String lockkey) {
        RLock rlock = redissonClient.getLock(lockkey);
        rlock.forceUnlock();
    }
}
