package com.hk.jdk.concurrent;

public class Wait {

    public static void main(String[] args) {

        final Object obj = new Object();

        new Thread(() -> {

            synchronized (obj) {

                try {
                    System.out.println("线程陷入了等待！");
                    obj.wait();
                    System.out.println("线程被唤醒了！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();


        try {
            Thread.sleep(3 *1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (obj) {
                obj.notify();
                System.out.println("唤醒者收束");
            }
        }).start();

    }
}
