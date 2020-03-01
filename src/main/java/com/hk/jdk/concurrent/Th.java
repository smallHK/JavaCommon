package com.hk.jdk.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Th {


    private static void inter() {

        Thread t = new Thread(() -> {
            List<Double> d = new ArrayList<>();
            Random r = new Random(99);
            int count = 100_00000;
            while (count > 0) {
                d.add(r.nextDouble());
                count--;
            }

            System.out.println("中断状态： " + Thread.interrupted());
            System.out.println("sleep forever!");
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                System.out.println("中断状态： " + Thread.interrupted());
                System.out.println("线程被中断！");
                System.out.println("中断状态： " + Thread.interrupted());
//                e.printStackTrace();
            }
        });

        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("开始中断测试！");
        t.interrupt();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("再次中断！");
        t.interrupt();
    }

    public static void main(String[] args) {

        inter();
    }
}
