package com.liugeng.cloud.study.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTaskTest extends RecursiveTask<Integer> {

    /***计算1+2+3+4 通过ForkJoin框架计算***/

    /**阈值*/
    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    public CountTaskTest(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 判断是否可拆分最小任务
        boolean canCompute = (end - start) < THRESHOLD;
        if (canCompute){
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值
            int middle = (start + end) / 2;
            CountTaskTest leftTask = new CountTaskTest(start,middle);
            CountTaskTest rightTask = new CountTaskTest(middle + 1,end);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            // 获取任务结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        // ForkJoin类
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTaskTest countTaskTest = new CountTaskTest(1,4);
        Future<Integer> result = forkJoinPool.submit(countTaskTest);
        try {
            System.out.println(result.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
