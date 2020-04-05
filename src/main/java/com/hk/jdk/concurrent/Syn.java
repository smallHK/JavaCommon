package com.hk.jdk.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Syn {
    private static void firstSync() {
        class S extends AbstractQueuedSynchronizer {
            @Override
            protected boolean tryAcquire(int arg) {
                System.out.println("start: " + getState());
                if(compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    System.out.println("success: " + getState());
                    return true;
                }
                System.out.println("false: " + getState());
                return false;
            }

            @Override
            protected boolean tryRelease(int arg) {
                if(!isHeldExclusively())
                    throw  new IllegalMonitorStateException();
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

        firstSync();
    }
}
