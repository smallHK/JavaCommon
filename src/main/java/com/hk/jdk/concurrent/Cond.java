package com.hk.jdk.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cond {

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
        Cond c = new Cond();
        c.firstCond();
    }
}
