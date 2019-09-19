package com.liugeng.cloud.study.thread.threadpool;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) throws Exception {
        DefaultThreadPool defaultThreadPool = new DefaultThreadPool();
        for (int i = 0; i < 20; i++) {
            if(i > 18){
                TimeUnit.MILLISECONDS.sleep(2000);
            }
            final int k = i;
            defaultThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("我是任务" + k);
                }
            });
        }
        defaultThreadPool.shutdown();
        System.out.println(defaultThreadPool.getJobSize());
    }
}
