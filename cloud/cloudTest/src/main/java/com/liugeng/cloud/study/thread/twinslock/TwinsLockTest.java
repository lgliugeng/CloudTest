package com.liugeng.cloud.study.thread.twinslock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TwinsLockTest {

    public static void main(String[] args) throws Exception {
        final Lock lock = new TwinsLock();
        class Worker implements Runnable{
            @Override
            public void run() {
                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            Thread thread = new Thread(worker,"线程" + i);
            thread.start();
        }
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("休息一秒……");
        }
    }
}
