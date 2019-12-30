package com.hk.se;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueT {

    //超时阻塞
    private static void timeOutBlocking() {
        BlockingQueue queue = new LinkedBlockingQueue();

        new Thread(() -> {
            try {

                System.out.println("start sleep");
                Thread.sleep(5 * 1000);
                queue.offer("ele", 3, TimeUnit.SECONDS);
                System.out.println("Element has been put!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {

            try {
                //3s 获取超时
                System.out.println("gain ele: " + queue.poll(3, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

    }


    //永久阻塞操作
    private static void foreverBlocking() {

        BlockingQueue queue = new LinkedBlockingQueue();

        new Thread(() -> {
            try {

                System.out.println("start sleep");
                Thread.sleep(3 * 1000);
                queue.put("ele");
                System.out.println("Element has been put!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {

            try {
                System.out.println("gain ele: " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

    }

    //阻塞队列测试代码
    public static void main(String[] args) {

        timeOutBlocking();
    }
}
