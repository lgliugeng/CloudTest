package com.liugeng.cloud.common.redission;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedissonTest {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.13.184:6379");
        //config.useClusterServers().addNodeAddress("redis://192.168.13.178:6379").addNodeAddress("redis://192.168.13.184:6379");
        final RedissonClient redisson = Redisson.create(config);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RLock lock = redisson.getLock("testKey");
                        if(lock.tryLock(1L,5L, TimeUnit.SECONDS)){
                            System.out.println(Thread.currentThread().getName() + "抢到锁，休息0.1秒");
                            Thread.sleep(100);
                            lock.unlock();
                        }else{
                            System.out.println(Thread.currentThread().getName() + "没抢到锁");
                        }
                    }catch (Exception e){

                    }
                }
            }).start();
        }
    }
}
