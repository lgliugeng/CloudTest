package com.liugeng.cloud.study.thread.SpinLock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
* @Description:    CLHLock自旋锁
* @Author:         liugeng
* @CreateDate:     2019/7/9 18:44
* @UpdateUser:     liugeng
* @UpdateDate:     2019/7/9 18:44
* @UpdateRemark:   修改内容
*/
public class CLHLock {
    /**
     * CLH锁节点状态 - 每个希望获取锁的线程都被封装为一个节点对象
     */
    private static class CLHNode {

        /**
         * 默认状态为true - 即处于等待状态或者加锁成功(换言之，即此节点处于有效的一种状态)
         */
        volatile boolean active = true;

    }

    /**
     * 隐式链表最末等待节点
     */
    private volatile CLHNode tail = null;

    /**
     * 线程对应CLH节点映射
     */
    private ThreadLocal<CLHNode> currentThreadNode = new ThreadLocal<>();

    /**
     * 原子更新器
     */
    private static final AtomicReferenceFieldUpdater UPDATER = AtomicReferenceFieldUpdater
            .newUpdater(
                    CLHLock.class,
                    CLHNode.class,
                    "tail");

    /**
     * CLH加锁
     */
    public void lock(String taskId) {

        CLHNode cNode = currentThreadNode.get();

        if (cNode == null) {
            cNode = new CLHNode();
            currentThreadNode.set(cNode);
        }
        // 通过这个操作完成隐式链表的维护，后继节点只需要在前驱节点的locked状态上自旋
        CLHNode predecessor = (CLHNode) UPDATER.getAndSet(this, cNode);
        if (predecessor != null) {
            // 自旋等待前驱节点状态变更 - unlock中进行变更
            while (predecessor.active) {
                //System.out.println("自旋中......." + predecessor);
            }
        }

        // 没有前驱节点表示可以直接获取到锁，由于默认获取锁状态为true，此时可以什么操作都不执行
        // 能够执行到这里表示已经成功获取到了锁
    }

    /**
     * CLH释放锁
     */
    public void unlock(String taskId) {

        CLHNode cNode = currentThreadNode.get();

        // 只有持有锁的线程才能够释放
        if (cNode == null || !cNode.active) {
            return;
        }
        // 从映射关系中移除当前线程对应的节点
        currentThreadNode.remove();

        // 尝试将tail从currentThread变更为null，因此当tail不为currentThread时表示还有线程在等待加锁
        if (!UPDATER.compareAndSet(this, cNode, null)) {
            System.out.println("解锁"+taskId);
            // 不仅只有当前线程，还有后续节点线程的情况 - 将当前线程的锁状态置为false，因此其后继节点的lock自旋操作可以退出
            cNode.active = false;
        }

    }

    /**
     * 用例
     *
     * @param args
     */
    public static void main(String[] args) {

        final CLHLock lock = new CLHLock();

        for (int i = 1; i <= 10; i++) {
            new Thread(generateTask(lock, String.valueOf(i))).start();
        }

    }

    private static Runnable generateTask(final CLHLock lock, final String taskId) {
        return () -> {
            lock.lock(taskId);
            System.out.println(taskId + "加锁完成");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }

            System.out.println(String.format("Thread %s Completed", taskId));
            lock.unlock(taskId);
        };
    }
}
