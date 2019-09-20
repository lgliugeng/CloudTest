package com.liugeng.cloud.study.thread.twinslock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TwinsLock implements Lock {

    /**获取同步器对象*/
    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer{

        final Lock lock = new ReentrantLock();

        Sync(int count){
            if (count <= 0){
                throw new IllegalArgumentException("参数异常");
            }
            // 定义同步器共享访问线程数
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for (;;){
                // 循环获取当前锁的状态，当锁的状态大于等于0且CAS更新成功时表示获取锁，否则没有获取
                lock.lock();
                try {
                    int currentState = getState();
                    int newState = currentState - reduceCount;
                    if (newState >= 0 && compareAndSetState(currentState,newState)){
                        System.out.println(Thread.currentThread().getName()+"获取锁，此时状态为"+getState());
                        return newState;
                    }
                }finally {
                    lock.unlock();
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            for (;;){
                lock.lock();
                try {
                    // 循环获取当前锁的状态，当返回的结果为true时表示成功释放锁
                    int currentState = getState();
                    int newState = currentState + returnCount;
                    if(compareAndSetState(currentState,newState)){
                        System.out.println(Thread.currentThread().getName()+"释放锁，此时状态为"+getState());
                        return true;
                    }
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public void lock() {
        // 通过自定义同步器加锁
        sync.tryAcquireShared(1);
    }

    @Override
    public void unlock() {
        // 通过自定义同步器解锁
        sync.tryReleaseShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
