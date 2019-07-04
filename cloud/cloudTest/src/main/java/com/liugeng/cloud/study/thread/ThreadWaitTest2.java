package com.liugeng.cloud.study.thread;

public class ThreadWaitTest2 {
    public static void main(String[] args) throws Exception{
        System.out.println("主线程开始");
        ThreadTest2 tt = new ThreadTest2(Thread.currentThread());
        Thread t = new Thread(tt);
        t.start();
        t.join();//等待调用线程执行完后后面的程序才会执行
        System.out.println("主线程继续...");
        Thread.sleep(2000);
        System.out.println("主线程结束");

        System.out.println(t.getName());
    }

}

class ThreadTest2 implements Runnable{

    private Thread main;

    ThreadTest2(Thread thread){
        this.main = thread;
    }

    @Override
    public void run() {
        try {
            synchronized (main){
                //main.notify();
                System.out.println(Thread.currentThread().getName() + "开始等待");
                //this.wait();

                System.out.println(Thread.currentThread().getName() + "开始执行");
                System.out.println(Thread.currentThread().getName() + "正在执行");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "结束执行");
                Thread.sleep(1000);
            }
        }catch (Exception e){
            System.out.println(Thread.currentThread().getName() + "出现异常");
        }
    }
}
