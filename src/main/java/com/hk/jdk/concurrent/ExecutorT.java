package com.hk.jdk.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecutorT {



    //测试Executors提供的默认线程工场
    private void defaultFac() {
        ThreadFactory f = Executors.defaultThreadFactory();
        Thread ft1 = f.newThread(() ->{});
        System.out.println(ft1.getName());
        System.out.println(ft1.isDaemon());
        System.out.println(ft1.getThreadGroup().getName());

        ThreadFactory f2 = Executors.defaultThreadFactory();
        Thread f2t1 = f2.newThread(()-> {});
        System.out.println(f2t1.getName());
        System.out.println(f2t1.getThreadGroup().getName());

        ThreadGroup group = Thread.currentThread().getThreadGroup();
        System.out.println(group.getName());
    }

    public static void main(String[] args) {

        var e = new ExecutorT();
        e.defaultFac();
    }
}
