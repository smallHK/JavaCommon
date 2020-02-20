package com.hk.netty;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;

public class Utils {



    //netty同步库实现异步
    private void async() {

        EventExecutorGroup executor = new DefaultEventExecutorGroup(5);

        Future<?> f = executor.submit(() -> {
            System.out.println("Task start!!");
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task finished!");
        });
        f.addListener(e -> {
            System.out.println("Asyn call!!!");
        });

        executor.shutdownGracefully();

        System.out.println("Main finished!");

    }

    public static void main(String[] args) {

        var main = new Utils();
        main.async();
    }
}
