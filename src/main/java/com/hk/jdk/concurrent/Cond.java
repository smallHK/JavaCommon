package com.hk.jdk.concurrent;

import com.google.inject.internal.cglib.core.$ClassNameReader;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cond {


    private static void condAwait() {

        Lock lock = new ReentrantLock();
        Condition cond = lock.newCondition();
        try {
            lock.lock();
            System.out.println("before wait");
//            cond.awaitNanos(TimeUnit.SECONDS.toSeconds(3));
            System.out.println(cond.awaitNanos(TimeUnit.SECONDS.toSeconds(3)));
            System.out.println("after wait");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //Condition测试
    private void firstCond() {
        Lock lock = new ReentrantLock();
        Condition cond = lock.newCondition();

        new Thread(() -> {

            lock.lock();
            try {
                System.out.println("该线程等待了！");
                cond.await();
                System.out.println("该线程被唤醒了！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("开始唤醒！");
        lock.lock();
        try {
            cond.signal();
        } finally {
            lock.unlock();
        }


    }

    public static void main(String[] args) {
//        Cond c = new Cond();
//        c.firstCond();

        condAwait();
    }
}
