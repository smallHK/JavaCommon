package com.hk.jdk.concurrent;

import java.util.concurrent.locks.LockSupport;

public class LockT {


    private static void park() {

        Thread t = new Thread(() -> {
            System.out.println("线程开始！");
            LockSupport.park();
            System.out.println("线程恢复！");
        });

        t.start();
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("开始唤醒");
        LockSupport.unpark(t);

    }

    public static void main(String[] args) {
        park();
    }
}
