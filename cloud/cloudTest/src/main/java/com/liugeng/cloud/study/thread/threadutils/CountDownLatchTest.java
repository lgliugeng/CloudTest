package com.liugeng.cloud.study.thread.threadutils;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    /**计数器可指定数，当运行线程数达到指定数才不会阻塞，只可使用一次*/
    static CountDownLatch c = new CountDownLatch(2);
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                c.countDown();
                System.out.println(2);
                c.countDown();
            }
        }).start();
        c.await();
        System.out.println("3");
    }

}
