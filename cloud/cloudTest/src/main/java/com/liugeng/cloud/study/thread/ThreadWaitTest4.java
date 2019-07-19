package com.liugeng.cloud.study.thread;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        /*ThreadWaitTest4 tDemo = new ThreadWaitTest4();
        tDemo.start();*/
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        for (int i = 0; i < 100000; i++) {
            System.out.println("main添加" + i);
            copyOnWriteArrayList.add(i);
        }
        System.out.println("添加完成" + copyOnWriteArrayList.size());
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
        print(copyOnWriteArrayList);
    }

    public static void print(CopyOnWriteArrayList copyOnWriteArrayList){
        ExecutorService pool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < copyOnWriteArrayList.size(); i++) {
            final int k = i;
            pool.execute(()->{
                System.out.println(Thread.currentThread().getName() + "输出" + k);
            });
        }
    }
}
