package com.liugeng.cloud.service.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockQueueDemoService {

    public static void main(String[] args) {
        BlockingQueue<QueueInfo> blockingQueue = new ArrayBlockingQueue<QueueInfo>(5);
        Producer p = new Producer(blockingQueue);
        Customer c = new Customer(blockingQueue);
        ExecutorService pp = Executors.newFixedThreadPool(5);
        ExecutorService cc = Executors.newFixedThreadPool(10);
        pp.submit(p);
        cc.submit(c);
    }
}

class Producer implements Runnable{

    private QueueInfo queueInfo;

    public QueueInfo getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(QueueInfo queueInfo) {
        this.queueInfo = queueInfo;
    }

    private BlockingQueue<QueueInfo> queueInfoQueue;

    public  Producer(BlockingQueue<QueueInfo> queueInfoQueue){
        this.queueInfoQueue = queueInfoQueue;
    }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread()+"生产：苹果"+i);
                    queueInfoQueue.put(new QueueInfo("苹果"+i));
                }
            }catch (Exception e){

            }
        }
}

class Customer implements Runnable{

    private  BlockingQueue<QueueInfo> queueInfoQueue;

    public  Customer(BlockingQueue<QueueInfo> queueInfoQueue){
        this.queueInfoQueue = queueInfoQueue;
    }

    @Override
    public void run() {
        try {
            while(true){
                if(queueInfoQueue.isEmpty()){
                    System.out.println(Thread.currentThread()+"没有了，等待中。。。");
                    Thread.sleep(1000);
                    if(queueInfoQueue.isEmpty()){
                        System.out.println("卖完了");
                        break;
                    }
                }else{
                    System.out.println(Thread.currentThread()+"消费："+queueInfoQueue.take().getName());
                }
            }
        }catch (Exception e){

        }
    }
}

class QueueInfo{
    private String name;

    public QueueInfo(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}