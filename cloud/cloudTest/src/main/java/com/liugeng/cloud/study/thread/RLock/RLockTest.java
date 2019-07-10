package com.liugeng.cloud.study.thread.RLock;

import java.util.concurrent.locks.ReentrantLock;

/**
* @Description:    可重入锁
* @Author:         liugeng
* @CreateDate:     2019/7/10 18:12
* @UpdateUser:     liugeng
* @UpdateDate:     2019/7/10 18:12
* @UpdateRemark:   修改内容
*/
public class RLockTest {
    public static void main(String[] args) {
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        Thread t1 = new Thread(synchronizedTest);
        Thread t2 = new Thread(synchronizedTest);
        Thread t3 = new Thread(reentrantLockTest);
        Thread t4 = new Thread(reentrantLockTest);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

class SynchronizedTest implements Runnable{

    public synchronized void get(){//对于同一个对象，此处为this,get()的锁和set()方法的锁是相同的锁，线程拿到get()的锁即可进入set()方法中
        System.out.println("SynchronizedTest类get执行的线程Id:" + Thread.currentThread().getId());
        set();
    }

    public synchronized void set(){
        System.out.println("SynchronizedTest类set执行的线程Id:" + Thread.currentThread().getId());
    }

    @Override
    public void run() {
        get();
    }
}

class ReentrantLockTest implements Runnable{

    ReentrantLock reentrantLock = new ReentrantLock();

    public void get(){
        reentrantLock.lock();
        System.out.println("ReentrantLockTest类get执行的线程Id:" + Thread.currentThread().getId());
        set();
        reentrantLock.unlock();
    }

    public synchronized void set(){
        reentrantLock.lock();
        System.out.println("ReentrantLockTest类set执行的线程Id:" + Thread.currentThread().getId());
        reentrantLock.unlock();
    }

    @Override
    public void run() {
        get();
    }
}
