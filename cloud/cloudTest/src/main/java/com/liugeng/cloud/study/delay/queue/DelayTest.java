package com.liugeng.cloud.study.delay.queue;

import java.util.Random;

public class DelayTest{

    public static void main(String[] args) throws Exception {
        DelayEvent delayEvent = new DelayEvent();
        while (true) {
            Thread.sleep((new Random().nextInt(10) + 1) * 1000);
            long startTime = System.currentTimeMillis();
            System.out.println("启用支付,当前时间：" + startTime);
            long endTime = (new Random().nextInt(10000) + 1) + startTime;
            long id = Long.valueOf(new Random().nextInt(1000));
            delayEvent.put(id,"支付超时",endTime);
            System.out.println("支付超时时间：" + endTime);
            System.out.println("业务id:" + id + "事件放入延迟队列，等待支付," + (endTime-startTime) + "毫秒后将超时");
        }
    }
}

class DelayEvent extends DelayCache {

    @Override
    public void put(long id, String desc, long endTime) {
        super.put(id, desc, endTime);
    }

    @Override
    public void invoke(long id, String desc, long endTime) {
        System.out.println("当前时间：" + System.currentTimeMillis());
        System.out.println("结束时间：" + endTime);
        System.out.println("业务id:" + id + "已出延迟队列，开始执行事件：" + desc);
    }
}
