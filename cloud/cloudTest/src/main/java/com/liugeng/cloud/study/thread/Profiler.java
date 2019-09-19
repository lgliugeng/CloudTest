package com.liugeng.cloud.study.thread;

import java.util.concurrent.TimeUnit;

/**
* 时间差值计算，aop计算方法耗时
*/
public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<>();

    public static void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static Long end(){
        return System.currentTimeMillis()-TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws Exception {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Profiler.end());
    }

}
