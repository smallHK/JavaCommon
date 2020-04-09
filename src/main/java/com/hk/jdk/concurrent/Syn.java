package com.hk.jdk.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Syn {

    private static void sem() {

        Semaphore s = new Semaphore(1);

        new Thread(() -> {
            System.out.println("A start!");
            try {
                s.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A finished!");
            s.release();
        }).start();

        new Thread(() -> {
            System.out.println("B start!");
            try {
                s.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B finished!");
            s.release();
        }).start();

        new Thread(() -> {
            System.out.println("C start!");
            try {
                s.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C finished!");
            s.release();
        }).start();

    }

    private static void own() {
        class S extends AbstractOwnableSynchronizer {
            void run() {
                System.out.println(getExclusiveOwnerThread());
                setExclusiveOwnerThread(Thread.currentThread());
                System.out.println(getExclusiveOwnerThread().getName());
                setExclusiveOwnerThread(null);
                System.out.println(getExclusiveOwnerThread());
            }

            void set(){
                setExclusiveOwnerThread(Thread.currentThread());
            }
            void print() {
                System.out.println(getExclusiveOwnerThread());
            }
        }
        S s = new S();
        s.run();
        new Thread(() -> {
            s.set();
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程");
            s.print();
        }).start();

        try {
            Thread.sleep(1*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.print();


    }


    private static void syncState() {
        class S extends AbstractQueuedSynchronizer {
            void run() {
                System.out.println("状态： " + getState());
                setState(121323);
                System.out.println("状态： " + getState());
                System.out.println(compareAndSetState(1, 123));
                System.out.println(compareAndSetState(121323, 123));
                System.out.println("状态： " + getState());
            }

        }
        S s = new S();
        s.run();
    }

    private static void firstSync() {
        class S extends AbstractQueuedSynchronizer {
            @Override
            protected boolean tryAcquire(int arg) {
                System.out.println("start: " + getState());
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    System.out.println("success: " + getState());
                    return true;
                }
                System.out.println("false: " + getState());
                return false;
            }

            @Override
            protected boolean tryRelease(int arg) {
                if (!isHeldExclusively())
                    throw new IllegalMonitorStateException();
                setExclusiveOwnerThread(null);
                setState(0);
                return true;
            }

            @Override
            protected boolean isHeldExclusively() {
                return getExclusiveOwnerThread() == Thread.currentThread();
            }
        }
        S s = new S();
        s.acquire(1);
        s.release(1);
    }

    public static void main(String[] args) {

//        firstSync();
//        syncState();
//        own();
        sem();
    }
}
