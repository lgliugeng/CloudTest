package com.liugeng.cloud.study.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 元空间溢出
 */
public class JavaMethodAreaOOM {

    //-Xms30M -Xmx30M -Xmn10M -XX:+PrintGCDetails -XX:MetaspaceSize=6M -XX:MaxMetaspaceSize=6M -XX:+UseConcMarkSweepGC -XX:+HeapDumpOnOutOfMemoryError

    static class OOMObject{
    }

    static int i = 0;

    static {
        i++;
    }

    public static void main(String[] args) throws Exception {
        Thread.sleep(10000);
        System.out.println(i);
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invoke(obj, args);
                }
            });
            enhancer.create();
        }
    }

    static {
        i = i + 2;
    }
}
