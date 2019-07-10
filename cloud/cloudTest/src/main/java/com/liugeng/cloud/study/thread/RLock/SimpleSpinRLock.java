package com.liugeng.cloud.study.thread.RLock;

import java.util.concurrent.atomic.AtomicReference;

/**
* @Description:    非公平简单自旋锁（自旋锁对同一个线程加多次锁时会造成死锁，增加计数改成可重入锁）
* @Author:         liugeng
* @CreateDate:     2019/7/9 18:50
* @UpdateUser:     liugeng
* @UpdateDate:     2019/7/9 18:50
* @UpdateRemark:   修改内容
*/
public class SimpleSpinRLock {

    /**
     * 维护当前拥有锁的线程对象
     */
    private AtomicReference<Thread> owner = new AtomicReference<>();

    private int count = 0;

    public void lock(int taskId) {
        Thread currentThread = Thread.currentThread();
        if(currentThread == owner.get()){
            count++;
            System.out.println(taskId + "已获得锁，重复加锁计数加1,计数：" + count + "，不进行自旋");
            return;
        }
        // 只有owner没有被加锁的时候，才能够加锁成功，否则自旋等待
        while (!owner.compareAndSet(null, currentThread)) {//将当前线程设置到owner中，设置成功才算加锁退出自旋并可进行解锁，否则尝试自旋等待加入

        }
    }

    public void unlock(int taskId) {
        Thread currentThread = Thread.currentThread();
        if(currentThread == owner.get()){
            if(count != 0){
                System.out.println(taskId + "可重入次数：" + count + "不释放锁");
                count--;
                try {
                    if(count == 0){
                        System.out.println(taskId + "可重入次数：" + count + ",等待释放锁");
                    }
                    Thread.sleep(500);//睡0.5秒
                    unlock(taskId);
                }catch (Exception e){

                }
            }else{
                // 只有锁的owner才能够释放锁，其它的线程因为无法满足Compare，因此不会Set成功
                owner.compareAndSet(currentThread, null);
            }
        }
    }

    public static void main(String[] args) {
        final SimpleSpinRLock simpleSpinLock = new SimpleSpinRLock();
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            new Thread(generateTask(simpleSpinLock,taskId)).start();
        }
    }

    private static Runnable generateTask(final SimpleSpinRLock lock, final int taskId){
        return ()->{
            lock.lock(taskId);
            System.out.println(taskId + "加锁");
            lock.lock(taskId);
            lock.lock(taskId);
            try {
                System.out.println(taskId + "业务中");
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            lock.unlock(taskId);
            System.out.println(taskId + "解锁");
        };
    }
}
