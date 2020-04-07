package com.liugeng.cloud.study.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法区溢出（运行时常量池内存溢出）(jdk1.8之前，1.8之后移除了永久代)
 */
public class RunConstantOOMTest {

    //-Xms30M -Xmx30M -Xmn10M -XX:+PrintGCDetails -XX:MetaspaceSize=3M -XX:MaxMetaspaceSize=3M -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }

    }
}
