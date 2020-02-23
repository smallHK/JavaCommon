package com.hk.jdk.concurrent;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;

public class Shutdown {

    //挂载关闭钩子
    private void addShutdownHook() {
        Thread shutdownThread = new Thread(() -> {
            //
        });
        shutdownThread.setName("My-ShutDown");
        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }


    //列出所有挂载的钩子
    private void listShutdownHook() throws Exception {
        String className = "java.lang.ApplicationShutdownHooks";
        Class<?> clazz = Class.forName(className);
        Field field = clazz.getDeclaredField("hooks");
        field.setAccessible(true);
        synchronized (clazz) {
            IdentityHashMap<Thread, Thread> map = (IdentityHashMap<Thread, Thread>)field.get(clazz);
            for (Thread thread: map.keySet()) {
                System.out.println(thread);
            }
        }

    }

    public static void main(String[] args) {

        var main = new Shutdown();
        main.addShutdownHook();

        try {
            main.listShutdownHook();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
