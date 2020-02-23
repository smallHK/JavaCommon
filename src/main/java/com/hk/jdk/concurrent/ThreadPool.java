package com.hk.jdk.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;




public class ThreadPool {

    public static void main(String[] args) {

        int coreNum = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(coreNum, coreNum, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());



        System.out.println("before submit!");

        for(int i = 0 ; i < 10; i++) {
            executor.submit(new UnitTask(i));
        }

        System.out.println("after submit!");

        executor.shutdown();
//        try {
//            executor.awaitTermination(0, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("complete task: " + executor.getCompletedTaskCount());
        System.out.println("finish!");


    }
}
