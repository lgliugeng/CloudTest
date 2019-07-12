package com.liugeng.cloud.study.thread.RLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NonFairLockTest {

    private static ReentrantLock unFairLock = new ReentrantLock();//非公平锁

    public static void main(String[] args) {

        TestNonTask testTask = new TestNonTask(unFairLock);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(testTask);
            thread.setName("线程" + i);
            thread.start();
        }

    }
}

class TestNonTask implements Runnable{

    private Lock lock;

    public TestNonTask(Lock lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {//每个线程执行一次时切换，每个线程执行完一次释放了锁将重新竞争锁，竞争是非公平的，非公平锁吞吐效率高，但容易使线程得不到运行
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
