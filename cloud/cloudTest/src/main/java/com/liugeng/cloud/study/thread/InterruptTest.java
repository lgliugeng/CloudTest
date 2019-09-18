package com.liugeng.cloud.study.thread;

public class InterruptTest {

    private volatile static  Boolean interrupt = false;

    public static void main(String[] args)throws Exception {

        Thread t1 = new Thread(new BusyThread());
        t1.setName("busy线程");
        Thread t2 = new Thread(new SleepThread());
        t2.setName("sleep线程");
        t1.start();
        t2.start();
        Thread.sleep(5);
        //判断中断标识
        interrupt = true;
        //将运行中的线程修改中断状态，若不加的话，阻塞中的线程会无法中断，sleep中的1仍会打印，加的话将会抛出异常，中断后续的操作
        t1.interrupt();
        t2.interrupt();
    }

    static class BusyThread implements Runnable{
        @Override
        public void run() {
            while (!interrupt) {
                System.out.println(Thread.currentThread().getName() + "当前状态" + Thread.currentThread().isInterrupted());
            }
            System.out.println(Thread.currentThread().getName() + "当前状态" + Thread.currentThread().isInterrupted());
        }
    }

    static class SleepThread implements Runnable{
        @Override
        public void run() {
            while (!interrupt) {
                try {
                    System.out.println(Thread.currentThread().getName() + "睡眠五秒……当前状态" + Thread.currentThread().isInterrupted());
                    Thread.sleep(30);
                    //中断状态修改会抛异常，1将不会打印
                    System.out.println(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "当前状态" + Thread.currentThread().isInterrupted());
        }
    }
}
