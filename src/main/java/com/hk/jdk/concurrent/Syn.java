package com.hk.jdk.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Syn {

    private static void exchange() {
        Exchanger<String> exchanger = new Exchanger<>();


        new Thread(() -> {
            System.out.println("Thread start!");
            try {
                System.out.println("thread: " + exchanger.exchange("thread send!"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread finish!");

        }).start();

        System.out.println("main sleep!");
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main sleep finish!");

        try {
            String re = exchanger.exchange("main send!");
            System.out.println("main: " + re);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void barrier() {
        CyclicBarrier barrier = new CyclicBarrier(4);

        class Task implements Runnable {
            int num = -1;
            Task(int num) {
                this.num = num;
            }
            @Override
            public void run() {
                System.out.println(num + " start!");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(num + " finish!");
            }
        }
        for(int i = 0; i < 4; i++) {
            if(i == 3) {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            new Thread(new Task(i)).start();
        }
    }

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
//        sem();
//        barrier();
        exchange();
    }
}
