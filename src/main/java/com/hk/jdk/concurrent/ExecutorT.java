package com.hk.jdk.concurrent;

import java.util.concurrent.*;

public class ExecutorT {


    //测试线程池池大小控制
    //初始线程数为0，提交任务时，增加新线程
    private void controlPoolSize() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, Long.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        class Task implements Runnable {
            @Override
            public void run() {
                System.out.println("Sleep forever!");
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        System.out.println(executor.getPoolSize());
        executor.submit(new Task());
        System.out.println(executor.getPoolSize());
        executor.submit(new Task());
        System.out.println(executor.getPoolSize());
        System.out.println(executor.getQueue().size());

        System.out.println("====");
        executor.setMaximumPoolSize(2);
        executor.setCorePoolSize(2);
        System.out.println(executor.getPoolSize());
        executor.submit(new Task());
        System.out.println(executor.getPoolSize());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(executor.getQueue().size());

    }



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
//        e.defaultFac();
        e.controlPoolSize();
    }
}
