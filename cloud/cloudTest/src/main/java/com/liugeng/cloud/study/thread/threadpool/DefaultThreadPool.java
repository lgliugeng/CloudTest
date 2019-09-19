package com.liugeng.cloud.study.thread.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static final int MAX_NUMBER = 10;

    private static final int DEFAULT_NUMBER = 5;

    private static final int MIN_NUMBER = 1;

    /**任务列表*/
    private final LinkedList<Job> jobs = new LinkedList();

    /**工人列表*/
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    private int workerNum = DEFAULT_NUMBER;

    private LongAdder threadNum = new LongAdder();

    private void initWorkers(int num){
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            this.threadNum.increment();
            Thread thread = new Thread(worker,"thread_pool-" + this.threadNum.intValue());
            thread.start();
        }
    }

    public DefaultThreadPool(){
        initWorkers(DEFAULT_NUMBER);
    }

    public DefaultThreadPool(int num){
        workerNum = num > MAX_NUMBER ? MAX_NUMBER : num < MIN_NUMBER ? MIN_NUMBER : num;
        initWorkers(workerNum);
    }

    @Override
    public void execute(Job job) {
        if (null != job){
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
        System.out.println("工人全部休息了");
    }

    @Override
    public void addWorker(int num) {
        synchronized (jobs){
            // 增加的num数不能超过最大的值
            if (num + this.workerNum > MAX_NUMBER ){
                num = MAX_NUMBER - this.workerNum;
            }
            // 初始化新增的工人
            initWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs){
            if(num > this.workerNum){
                throw new IllegalArgumentException("超过最大数");
            }
            int count = 0;
            while (count < num){
                Worker worker = workers.get(count);
                if(workers.remove(worker)){
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= num;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    class Worker implements Runnable{
        private volatile Boolean running = true;

        @Override
        public void run() {
            while (running){
                Job job = null;
                synchronized (jobs){
                    while (jobs.isEmpty()){
                        try {
                            // 没有任务进行等待
                            System.out.println("目前没有任务");
                            jobs.wait();
                        }catch (Exception e){
                            e.printStackTrace();
                            // 感知到外部的中断，清除标识，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    // 获取job
                    job = jobs.removeFirst();
                    if (null != job){
                        try {
                            System.out.print(Thread.currentThread().getName() + "即将执行");
                            job.run();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void shutdown(){
            running = false;
        }
    }
}
