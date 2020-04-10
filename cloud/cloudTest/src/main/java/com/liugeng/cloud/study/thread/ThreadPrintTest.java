package com.liugeng.cloud.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 交替线程打印
 */
public class ThreadPrintTest {

    public static volatile AtomicInteger atomicInteger = new AtomicInteger(0);
    public static String[] arr = {"a","b","c"};

    public static void main(String[] args) throws Exception {
        PrintThread a = new PrintThread("a",1);
        PrintThread b = new PrintThread("b",2);
        PrintThread c = new PrintThread("c",3);
        a.start();
        b.start();
        c.start();
    }
}

class PrintThread extends Thread{

    private int i;

    PrintThread(String name,int i){
        this.i = i;
        this.setName(name);
    }

    @Override
    public void run(){
        try {
            for (int j = 0; j < 10; j++) {
                synchronized (ThreadPrintTest.atomicInteger) {
                    if (ThreadPrintTest.arr[(ThreadPrintTest.atomicInteger.get() % 3)].equals(Thread.currentThread().getName())) {
                        ThreadPrintTest.atomicInteger.getAndIncrement();
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        ThreadPrintTest.atomicInteger.notifyAll();
                    } else {
                        ThreadPrintTest.atomicInteger.wait();
                    }
                }
            }
        }catch (Exception e){

        }
    }
}
