package com.liugeng.cloud.study.thread.connectionpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionTest {
    static ConnectionPool pool = new ConnectionPool(10);
    /**
    * 保证线程同时开始
    */
    static CountDownLatch start = new CountDownLatch(1);
    /**
     * 保证线程全部结束
     */
    static  CountDownLatch end;

    public static void main(String[] args) throws Exception {
        // 线程数量
        int threadCount = 100;
        end = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger get = new AtomicInteger();
        AtomicInteger noGet = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count,get,noGet),
                    "ConnectionRunnerThread");
            thread.start();
        }
        // 线程同时启动
        start.countDown();
        // 等待线程执行完成
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("get connection: " + get);
        System.out.println("no get connection " + noGet);

    }

    static class ConnectionRunner implements Runnable{
        int count;
        AtomicInteger get;
        AtomicInteger noGet;

        public ConnectionRunner(int count,AtomicInteger get,AtomicInteger noGet){
            this.count = count;
            this.get = get;
            this.noGet = noGet;
        }

        @Override
        public void run() {
            try {
                // 阻塞线程
                start.await();
            }catch (Exception e){

            }
            while (count > 0){
                try {
                    // 1000毫秒没有获取连接就返回null
                    Connection connection = pool.fetchConnection(1000);
                    if(null != connection){
                        try {
                            // 创建sql
                            connection.createStatement();
                            // 模拟连接
                            connection.commit();
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            // 释放连接
                            pool.releaseConnection(connection);
                            get.incrementAndGet();
                        }
                    } else {
                        noGet.incrementAndGet();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
