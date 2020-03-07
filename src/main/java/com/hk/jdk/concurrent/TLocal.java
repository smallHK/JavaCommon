package com.hk.jdk.concurrent;

public class TLocal {

    private static ThreadLocal<Integer> local = ThreadLocal.withInitial(() -> 1);

    private static int count = 1;
    private static void firstThreadLocal() {
        for(int i = 0; i < 10000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int c = local.get();
                System.out.println(c);
            }).start();
        }
    }

    public static void main(String[] args) {
        firstThreadLocal();
    }
}
