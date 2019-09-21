package com.liugeng.cloud.study.thread.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<T> {
    private Object[] items;

    private int addIndex,removeIndex,count;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size){
        items = new Object[size];
    }

    public void add(T t) throws InterruptedException{
        lock.lock();
        try {
            while (count == items.length){
                boolean flag = notFull.await(5, TimeUnit.SECONDS);
                if (!flag){
                    throw new InterruptedException("超时");
                }
            }
            items[addIndex] = t;
            if (++addIndex == items.length){
                addIndex = 0;
            }
            ++count;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public T remove() throws InterruptedException{
        lock.lock();
        try {
            while (count == 0){
                boolean flag = notEmpty.await(5, TimeUnit.SECONDS);
                if (!flag){
                    throw new InterruptedException("超时");
                }
            }
            Object obj = items[removeIndex];
            if (++removeIndex == items.length){
                removeIndex = 0;
            }
            --count;
            notFull.signal();
            return (T)obj;
        }finally {
            lock.unlock();
        }
    }
}
