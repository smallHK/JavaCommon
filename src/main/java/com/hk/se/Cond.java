package com.hk.se;

import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer<E> {
    final Lock lock = new ReentrantLock();

    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(E x) throws InterruptedException
    {
        lock.lock();
        try{
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try{
            while(count == 0)
                notEmpty.await();
            E x = (E)items[takeptr];
            if(++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}

//测试Condition功能
public class Cond {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        System.out.println("start");
        Condition con = lock.newCondition();
        lock.lock();
        try{
            try {
                System.out.println(con.awaitNanos(TimeUnit.SECONDS.toNanos(0)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock.unlock();
        }


        System.out.println("finish");
    }
}
