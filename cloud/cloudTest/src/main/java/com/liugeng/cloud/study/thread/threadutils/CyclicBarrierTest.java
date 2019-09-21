package com.liugeng.cloud.study.thread.threadutils;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    /**同步屏障必须指定达到数值的线程数都运行才不会阻塞后续程序，指定的方法优先于使用同步屏障的其他线程方法,可重复使用*/
    static CyclicBarrier c = new CyclicBarrier(2, new A());
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
                System.out.println(1);
            }
        }).start();
        try {
            c.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }
    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println(3);
        }
    }

}
