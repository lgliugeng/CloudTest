package com.liugeng.cloud.study.delay.queue;

import java.util.Objects;
import java.util.concurrent.*;

public abstract class DelayCache {

    public DelayQueue<DelayTask> delayQueue = new DelayQueue<DelayTask>();

    ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);

    public void put(long id,String desc,long endTime){
        DelayTask delayTask = new DelayTask(id,desc,endTime);
        delayQueue.put(delayTask);
    }

    public DelayCache() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    daemonTask();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(true);
        t.start();

    }

    public void daemonTask() throws InterruptedException{
        while (true) {
            DelayTask task = delayQueue.take();
            if (Objects.nonNull(task)) {
                cachedThreadPool.execute(()->{
                    invoke(task.getId(),task.getDesc(),task.getEndTime());
                });
            }
        }
    }

    public abstract void invoke(long id,String desc,long endTime);
}

class DelayTask implements Delayed {

    private long id;

    private String desc;

    private long endTime;

    public DelayTask(long id, String desc, long endTime) {
        this.id = id;
        this.desc = desc;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(endTime - System.currentTimeMillis(), TimeUnit.MICROSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if(o == null || ! (o instanceof DelayTask)) return 1;
        if(o == this) return 0;
        DelayTask s = (DelayTask)o;
        return endTime - s.endTime > 0 ? 1 :(endTime - s.endTime == 0 ? 0 : -1);
    }

    public static void main(String[] args) {
        System.out.println(System.nanoTime());
    }
}
