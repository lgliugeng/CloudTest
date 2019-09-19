package com.liugeng.cloud.study.thread.threadpool;

public interface ThreadPool<Job extends Runnable> {
    /**
    * 执行一个job
    */
    void execute(Job job);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 增加工作者线程
     */
    void addWorker(int num);

    /**
     * 减少工作者线程
     */
    void removeWorker(int num);

    /**
     * 获取等待执行的job数
     */
    int getJobSize();

}
