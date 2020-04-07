package com.liugeng.cloud.study.jvm;

/**
 * 栈深度溢出
 */
public class JavaStackOverFlowTest {

    //  -Xss5m 加大线程内存大小，加大栈深度 若是同时设置-XX:ThreadStackSize=1024 栈会变浅，即线程数减少
    //-Xms:30M -Xmx:30M -Xmn:10M -XX:+PrintGCDetails -XX:PermSize=2m -XX:MaxPermSize=2m -XX:+UseConcMarkSweepGC -Xss5m

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }


    public static void main(String[] args) throws Throwable {
        JavaStackOverFlowTest oom = new JavaStackOverFlowTest();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
