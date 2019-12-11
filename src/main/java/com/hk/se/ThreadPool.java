package com.hk.se;

import java.util.concurrent.Executors;


class UnitTask implements Runnable {

    int flag;

    UnitTask(int flag) {
        this.flag = flag;
    }

    public void run() {
        System.out.println("Task " + flag + " start!");

        try{
            Thread.sleep(5 * 100);
        }catch(Exception e) {

        }

        System.out.println("Task " + flag + " finished!");
    }

}

public class ThreadPool {

    public static void main(String[] args) {

        var executor = Executors.newFixedThreadPool(10);

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

        System.out.println("finish!");


    }
}
