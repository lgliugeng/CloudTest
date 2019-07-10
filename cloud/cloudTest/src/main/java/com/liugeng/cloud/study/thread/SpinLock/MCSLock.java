package com.liugeng.cloud.study.thread.SpinLock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
* @Description:    MCSLock自旋锁
* @Author:         liugeng
* @CreateDate:     2019/7/9 18:44
* @UpdateUser:     liugeng
* @UpdateDate:     2019/7/9 18:44
* @UpdateRemark:   修改内容
*/
public class MCSLock {

    /**
     * MCS锁节点
     */
    public static class MCSNode {

        /**
         * 后继节点
         */
        volatile MCSNode next;

        /**
         * 默认状态为等待锁
         */
        volatile boolean blocked = true;

    }

    /**
     * 线程到节点的映射
     */
    private ThreadLocal<MCSNode> currentThreadNode = new ThreadLocal<>();

    /**
     * 指向最后一个申请锁的MCSNode
     */
    volatile MCSNode queue;

    /**
     * 原子更新器
     */
    private static final AtomicReferenceFieldUpdater UPDATER           = AtomicReferenceFieldUpdater
            .newUpdater(
                    MCSLock.class,
                    MCSLock.MCSNode.class,
                    "queue");

    /**
     * MCS获取锁操作
     */
    public void lock() {
        MCSNode cNode = currentThreadNode.get();

        if (cNode == null) {
            // 初始化节点对象
            cNode = new MCSNode();
            currentThreadNode.set(cNode);
        }

        // 将当前申请锁的线程置为queue并返回旧值
        MCSNode predecessor = (MCSNode) UPDATER.getAndSet(this, cNode); // step 1

        if (predecessor != null) {
            // 形成链表结构(单向)
            predecessor.next = cNode; // step 2

            // 当前线程处于等待状态时自旋(MCSNode的blocked初始化为true)
            // 等待前驱节点主动通知，即将blocked设置为false，表示当前线程可以获取到锁
            while (cNode.blocked) {

            }
        } else {
            // 只有一个线程在使用锁，没有前驱来通知它，所以得自己标记自己为非阻塞 - 表示已经加锁成功
            cNode.blocked = false;
        }
    }

    /**
     * MCS释放锁操作
     */
    public void unlock() {
        // 获取当前线程对应的节点
        MCSNode cNode = currentThreadNode.get();

        if (cNode == null || cNode.blocked) {
            // 当前线程对应存在节点
            // 并且
            // 锁拥有者进行释放锁才有意义 - 当blocked未true时，表示此线程处于等待状态中，并没有获取到锁，因此没有权利释放锁
            return;
        }

        if (cNode.next == null && !UPDATER.compareAndSet(this, cNode, null)) {
            // 没有后继节点的情况，将queue置为空
            // 如果CAS操作失败了表示突然有节点排在自己后面了，可能还不知道是谁，下面是等待后续者
            // 这里之所以要忙等是因为上述的lock操作中step 1执行完后，step 2可能还没执行完
            while (cNode.next == null) {

            }
        }

        if (cNode.next != null) {
            // 通知后继节点可以获取锁
            cNode.next.blocked = false;

            // 将当前节点从链表中断开，方便对当前节点进行GC
            cNode.next = null; // for GC
        }

        // 清空当前线程对应的节点信息
        currentThreadNode.remove();

    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) {

        final MCSLock lock = new MCSLock();

        for (int i = 1; i <= 10; i++) {
            new Thread(generateTask(lock, String.valueOf(i))).start();
        }

    }

    private static Runnable generateTask(final MCSLock lock, final String taskId) {
        return () -> {
            lock.lock();

            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }

            System.out.println(String.format("Thread %s Completed", taskId));
            lock.unlock();
        };
    }
}
