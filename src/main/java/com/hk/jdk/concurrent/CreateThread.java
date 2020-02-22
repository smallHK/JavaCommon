package com.hk.jdk.concurrent;

import java.util.concurrent.ThreadFactory;

/**
 * 测试线程工厂
 */
public class CreateThread {

    class SimplyFactory implements ThreadFactory {

        int count = 0;
        @Override
        public Thread newThread(Runnable r) {

            Thread t = new Thread(r, "id: " + count);
            count++;
            return t;
        }
    }


    public void run() {
        var fac = new SimplyFactory();
        for(int i = 0;i < 10; i++) {
            Thread t = fac.newThread(() -> {});
            System.out.println(t.getId());
            System.out.println(t.getName());
        }
    }

    public static void main(String[] args) {
        var main = new CreateThread();
        main.run();
    }
}
