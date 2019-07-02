package com.liugeng.cloud.study.thread;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentTestOne {

    public static void main(String[] args) {
        CopyOnWriteArrayList<Long> a = new CopyOnWriteArrayList<>();//线程安全list
        a.add(1L);
        a.add(2L);
        a.add(3L);
        a.add(4L);
        a.add(5L);
        a.add(6L);
        a.add(7L);
        a.add(8L);
        ConcurrentTestOne.excute(a,1000);
    }

    /**
    * 方法说明   递归执行
    * @方法名    excute
    * @参数      [a, count]
    * @返回值    void
    * @异常
    * @创建时间  2019/7/2 10:37
    * @创建人    liugeng
    */
    public static void excute(CopyOnWriteArrayList<Long> a,int count){
        final CountDownLatch countDownLatch = new CountDownLatch(a.size());//信号量计数
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (Long l:a) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    if(l > 1L){//保留一个list值不移除
                        a.remove(l);
                        System.out.println("移除" + l);
                    }
                    countDownLatch.countDown();//递减
                }
            });
        }
        try {
            countDownLatch.await();//线程等待处理完成
            if(a.size() > 0){
                count --;
                if(count == 0){//递归达到次数退出递归
                    throw new Exception("退出递归");
                }
                System.out.println(Thread.currentThread().getName()+"递归次数" + count);
                excute(a,count);
            }
        }catch (Exception e){
            System.out.println("异常" + e.getMessage());
        }
    }
}