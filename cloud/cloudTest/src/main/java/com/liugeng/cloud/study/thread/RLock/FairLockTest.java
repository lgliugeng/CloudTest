package com.liugeng.cloud.study.thread.RLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLockTest {

    private static ReentrantLock reentrantLock = new ReentrantLock(true);//公平锁

    public static void main(String[] args) {

        TestTask testTask = new TestTask(reentrantLock);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(testTask);
            thread.setName("线程" + i);
            thread.start();
        }

    }
}

class TestTask implements Runnable{

    private Lock lock;

    public TestTask(Lock lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {//每个线程执行一次时切换，每个线程执行完一次释放锁后，当需要再执行时需要重新加入到等待队列中排队拿锁，公平锁影响吞吐量，唤醒线程的开销大
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "正在执行任务：" + i);
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        }
    }
}
