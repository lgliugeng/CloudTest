package com.liugeng.cloud.study.thread.SpinLock;

import java.util.concurrent.atomic.AtomicInteger;

/**
* @Description:    TicketLock自旋锁（简单排队）
* @Author:         liugeng
* @CreateDate:     2019/7/9 18:43
* @UpdateUser:     liugeng
* @UpdateDate:     2019/7/9 18:43
* @UpdateRemark:   修改内容
*/
public class TicketLock {

    /**
     * 当前正在接受服务的号码
     */
    private AtomicInteger serviceNum = new AtomicInteger(0);

    /**
     * 希望得到服务的排队号码
     */
    private AtomicInteger ticketNum  = new AtomicInteger(0);

    /**
     * 尝试获取锁
     *
     * @return
     */
    public int lock() {
        // 获取排队号
        int acquiredTicketNum = ticketNum.getAndIncrement();

        // 当排队号不等于服务号的时候开始自旋等待
        while (acquiredTicketNum != serviceNum.get()) {

        }

        return acquiredTicketNum;
    }

    /**
     * 释放锁
     *
     * @param ticketNum
     */
    public void unlock(int ticketNum) {
        // 服务号增加，准备服务下一位
        int nextServiceNum = serviceNum.get() + 1;

        // 只有当前线程拥有者才能释放锁
        serviceNum.compareAndSet(ticketNum, nextServiceNum);
    }

    public static void main(String[] args) {
        TicketLock ticketLock = new TicketLock();
        for (int i = 0; i < 10; i++) {
            new Thread(generateTask(ticketLock)).start();
        }
    }

    private static Runnable generateTask(final TicketLock lock) {
        return () -> {
            int acquiredTicketNum = lock.lock();
            System.out.println(acquiredTicketNum + "号请到服务窗口办理业务");
            try {
                System.out.println(acquiredTicketNum + "号业务处理中");
                Thread.sleep(3000);
            } catch (Exception e) {

            }

            System.out.println(acquiredTicketNum + "号业务处理完成");
            lock.unlock(acquiredTicketNum);
        };
    }
}
