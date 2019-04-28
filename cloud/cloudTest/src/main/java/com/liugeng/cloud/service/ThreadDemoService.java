package com.liugeng.cloud.service;

import com.liugeng.cloud.common.redis.RedisService;
import com.liugeng.cloud.entity.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ThreadDemoService {

    @Autowired
    private RedisService redisService;

    public ApiResult executorsPoolDemo(){
        ApiResult apiResult = new ApiResult("0","ok");
        ExecutorService pool = Executors.newFixedThreadPool(5);//创建线程池
        final StringBuffer stringBuffer = new StringBuffer();
        Lock lock = new ReentrantLock();
        int total = 100;
        redisService.set("A",total);
        for (int i = 0;i< 5;i++){//执行五次
            final int j = i;
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.lock();
                        task(j,stringBuffer);
                    }catch (Exception e){

                    }finally {
                        lock.unlock();
                    }
                }
            });
        }
        pool.shutdown();
        try {
            Thread.sleep(2000);
        }catch (Exception e){

        }
        int rr = redisService.get("A");
        System.out.println("redis余额："+rr);
        stringBuffer.append("redis余额："+rr);
        apiResult.setData(stringBuffer);
        return apiResult;
    }

    public StringBuffer task(final int a,final StringBuffer stringBuffer){
        if(a%2 == 0){
            int r1 = set(300);
            System.out.println("当前i值：" +a+ "线程"+Thread.currentThread().getName()+"存入300"+"余额："+r1 + "redis:"+ redisService.get("A"));
            stringBuffer.append("当前i值：" +a+ "线程"+Thread.currentThread().getName()+"存入300"+"余额："+r1 + "redis:"+ redisService.get("A") + "<br/>");
        }else{
            int total = redisService.get("A");
            if(total > 300){
                int r2 = get(300);
                System.out.println("当前i值：" +a+ "线程"+Thread.currentThread().getName()+"取出300"+"余额："+r2 + "redis:"+ redisService.get("A"));
                stringBuffer.append("当前i值：" +a+ "线程"+Thread.currentThread().getName()+"取出300"+"余额："+r2 + "redis:"+ redisService.get("A") + "<br/>");
            }else{
                int r2 = get(total);
                System.out.println("当前i值：" +a+ "线程"+Thread.currentThread().getName()+"取出"+total+"余额："+r2 + "redis:"+ redisService.get("A"));
                stringBuffer.append("当前i值：" +a+ "线程"+Thread.currentThread().getName()+"取出"+total+"余额："+r2 + "redis:"+ redisService.get("A") + "<br/>");
            }
        }
        return stringBuffer;
    }

    public int set(final int a){
        int total = redisService.get("A");
        total = total + a;
        redisService.set("A",total);
        return total;
    }

    public int get(final int a){
        int total = redisService.get("A");
        total = total - a;
        redisService.set("A",total);
        return total;
    }
}
