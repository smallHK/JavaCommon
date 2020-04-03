package com.hk.jdk.concurrent;

import java.util.concurrent.*;

public class ExecutorT {


    private static void callRun() {
        ThreadPoolExecutor service = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));
        service.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i = 0; i < 10; i++) {
            service.submit(() -> {
                try {
                    Thread.sleep(3 *1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务完成！");
            });
            System.out.println("任务" + i + "提交");
        }

        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("完成执行");

    }

    private static void await() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 2, Long.MAX_VALUE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));

        executor.submit(() -> {
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1完成");
        });

        executor.submit(() -> {
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2完成");
        });

        executor.submit(() -> {
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务3完成");
        });

        executor.submit(() -> {
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务4完成");
        });

        executor.submit(() -> {
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务5完成");
        });

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("完成执行");
    }

    //core线程设置0
    private void coreZero() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 2, Long.MAX_VALUE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        //七个任务不会拒绝
        for(int i = 0; i < 7; i++) {
            executor.submit(new SleepTask());
        }

        executor.shutdown();
    }


    //线程池关闭测试，即shutdown是否中断线程
    private void shutdownT() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, Long.MAX_VALUE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        executor.submit(() -> {
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello World!");
        });

        executor.shutdown();
    }


    //修改拒绝策略
    private void shutdownReject() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, Long.MAX_VALUE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));

        executor.shutdown();
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.submit(new SleepTask());
    }


    //引发线程池i默认拒绝任务
    private void rejectTask() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, Long.MAX_VALUE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));

        //七个任务不会拒绝
        for(int i = 0; i < 7; i++) {
            executor.submit(new SleepTask());
        }


        //再添加将会引发拒接
        try{
            executor.submit(new SleepTask());

        }catch (RejectedExecutionException e) {
            System.out.println("task has rejected!");
        }


        executor.shutdown();
        System.out.println("shut down!");

    }


    //测试线程池池大小控制
    //初始线程数为0，提交任务时，增加新线程
    private void controlPoolSize() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, Long.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());



        System.out.println(executor.getPoolSize());
        executor.submit(new SleepTask());
        System.out.println(executor.getPoolSize());
        executor.submit(new SleepTask());
        System.out.println(executor.getPoolSize());
        System.out.println(executor.getQueue().size());

        System.out.println("====");
        executor.setMaximumPoolSize(2);
        executor.setCorePoolSize(2);
        System.out.println(executor.getPoolSize());
        executor.submit(new SleepTask());
        System.out.println(executor.getPoolSize());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(executor.getQueue().size());

    }


    class SleepTask implements Runnable {
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
//        e.controlPoolSize();
//        e.shutdownT();
//        e.coreZero();
//        await();
        callRun();
    }
}
