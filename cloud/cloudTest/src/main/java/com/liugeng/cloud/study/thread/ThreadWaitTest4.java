package com.liugeng.cloud.study.thread;

public class ThreadWaitTest4 extends Thread {

    @Override
    public void run() {
        long begainTime = System.currentTimeMillis();
        int count = 0;
        System.out.println("开始");
        for (int i = 0; i < 5000000; i++) {
            Thread.yield();//Thread.yield()注释掉再次测试:
            count = count + (i + 1);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("用时:{"+(endTime - begainTime)+"}毫秒");
    }

    public static void main(String[] args) {
        ThreadWaitTest4 tDemo = new ThreadWaitTest4();
        tDemo.start();
    }
}
