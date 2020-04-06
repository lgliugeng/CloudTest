package com.liugeng.cloud.study.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出
 */
public class OOMTest {

    //JVM设置
    //  -XX:+PrintGCDetails 输出    -XX:+UseConcMarkSweepGC 设置并发收集器  -XX:+HeapDumpOnOutOfMemoryError 发生OOM时，自动生成DUMP文件。
    //-Xms30M -Xmx30M -Xmn10M -XX:+PrintGCDetails -XX:PermSize=10m -XX:MaxPermSize=10m -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError

    static class OOMObject{

    }


    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }
}
/**
 * Allocation Failure：表明本次引起GC的原因是因为在年轻代中没有足够的空间能够存储新的数据了。
 * GC （Allocation Failure）[ParNew(使用ParNew作为年轻代的垃圾回收期): 7936K（年轻代垃圾回收前的大小）->1024K（年轻代垃圾回收后的大小）(9216K 年轻代容量), 0.0351710 secs（回收时间）] 7936K（整个heap垃圾回收前的大小）->5613K（整个heap垃圾回收后的大小）(29696（整个heap容量大小）), 0.0352000 secs（回收时间）] [Times: user=0.14（Young GC用户耗时）sys=0.02（系统耗时）, real=0.03 secs（真实耗时）]
 *
 * [GC [1 CMS-initial-mark（初始标记，收集跟引用）: 11189K（老年代使用内存）(20480K老年代容量)] 12330K（对内存使用内存）(29696K对内存容量), 0.0018950 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *
 * concurrent mode failure一般发生在CMS GC 运行过程中,老年代空间不足，引发Full GC
 */
