package com.liugeng.cloud.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 交替线程打印奇偶数
 */
public class ThreadPrintTest1 {

    public static volatile AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) throws Exception {
        PrintThread1 a = new PrintThread1("奇数");
        PrintThread1 b = new PrintThread1("偶数");
        a.start();
        b.start();
    }
}

class PrintThread1 extends Thread{

    PrintThread1(String name){
        this.setName(name);
    }

    @Override
    public void run(){
        try {
            while (true) {
                synchronized (ThreadPrintTest1.atomicInteger) {
                    if (ThreadPrintTest1.atomicInteger.get() == 101) {
                        break;
                    }
                    if (ThreadPrintTest1.atomicInteger.get() % 2 == 1) {
                        if (Thread.currentThread().getName().equals("奇数")) {
                            System.out.println(Thread.currentThread().getName() + ":" + ThreadPrintTest1.atomicInteger.getAndIncrement());
                            ThreadPrintTest1.atomicInteger.notifyAll();
                        } else {
                            ThreadPrintTest1.atomicInteger.wait();
                        }
                    } else {
                        if (Thread.currentThread().getName().equals("偶数")) {
                            System.out.println(Thread.currentThread().getName() + ":" + ThreadPrintTest1.atomicInteger.getAndIncrement());
                            ThreadPrintTest1.atomicInteger.notifyAll();
                        } else {
                            ThreadPrintTest1.atomicInteger.wait();
                        }
                    }
                }
            }
        }catch (Exception e){

        }
    }
}
