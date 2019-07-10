package com.liugeng.cloud.study.thread.SpinLock;

import java.util.concurrent.atomic.AtomicReference;

/**
* @Description:    非公平简单自旋锁
* @Author:         liugeng
* @CreateDate:     2019/7/9 18:50
* @UpdateUser:     liugeng
* @UpdateDate:     2019/7/9 18:50
* @UpdateRemark:   修改内容
*/
public class SimpleSpinLock {

    /**
     * 维护当前拥有锁的线程对象
     */
    private AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {
        Thread currentThread = Thread.currentThread();
        // 只有owner没有被加锁的时候，才能够加锁成功，否则自旋等待
        while (!owner.compareAndSet(null, currentThread)) {//将当前线程设置到owner中，设置成功才算加锁退出自旋并可进行解锁，否则尝试自旋等待加入

        }
    }

    public void unlock() {
        Thread currentThread = Thread.currentThread();

        // 只有锁的owner才能够释放锁，其它的线程因为无法满足Compare，因此不会Set成功
        owner.compareAndSet(currentThread, null);
    }

    public static void main(String[] args) {
        final SimpleSpinLock simpleSpinLock = new SimpleSpinLock();
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            new Thread(generateTask(simpleSpinLock,taskId)).start();
        }
    }

    private static Runnable generateTask(final SimpleSpinLock lock,final int taskId){
        return ()->{
            lock.lock();
            System.out.println(taskId + "加锁");
            try {
                System.out.println(taskId + "业务中");
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            lock.unlock();
            System.out.println(taskId + "解锁");
        };
    }
}
