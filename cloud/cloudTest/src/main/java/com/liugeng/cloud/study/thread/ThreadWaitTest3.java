package com.liugeng.cloud.study.thread;

public class ThreadWaitTest3 {
    public static void main(String[] args) throws Exception{
        System.out.println("主线程开始");
        ThreadTest3 tt = new ThreadTest3(Thread.currentThread());
        Thread t = new Thread(tt);
        synchronized (Thread.currentThread()){//根据锁执行，谁先拿到锁谁先执行，main线程拿到锁先执行完main方法中代码释放锁后再执行拿到锁的其他线程方法
            t.start();
            Thread.currentThread().wait();
            //t.join();//等待调用线程执行完后后面的程序才会执行
            System.out.println("主线程继续...");
            Thread.sleep(2000);
            System.out.println("主线程结束");

            System.out.println(t.getName());
        }
    }

}

class ThreadTest3 implements Runnable{

    private Thread main;

    ThreadTest3(Thread thread){
        this.main = thread;
    }

    @Override
    public void run() {
        try {
            System.out.println(main.getName() + "正在执行，等待");
            synchronized (main){
                System.out.println(Thread.currentThread().getName() + "开始等待");
                System.out.println(Thread.currentThread().getName() + "开始执行");
                System.out.println(Thread.currentThread().getName() + "正在执行");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "结束执行");
                main.notify();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + "出现异常");
        }
    }
}
