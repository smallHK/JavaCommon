package com.hk.jdk.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Syn {

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
        syncState();
    }
}
