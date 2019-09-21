package com.liugeng.cloud.study.thread.condition;

public class ConditionTest {

    public static void main(String[] args) throws Exception {
        BoundedQueue<Integer> boundedQueue = new BoundedQueue<>(10);
        for (int i = 0; i < 9; i++) {
            if (i%2 == 0){
                System.out.println("插入值：" + i);
                boundedQueue.add(i);
            }
        }
        for (int i = 0; i < 9; i++) {
            Integer r = boundedQueue.remove();
            System.out.println("获取值：" + r);
        }
    }
}
