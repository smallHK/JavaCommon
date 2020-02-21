package com.hk.jdk.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Asyn {


    public void testAysn() {
        ExecutorService service = Executors.newFixedThreadPool(3);

        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> {
            System.out.println("task start!");
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task finished!");

            return "Hello World!";
        }, service);


        f.thenAccept(e -> {
            System.out.println("Async task!");
            System.out.println(e);
        });

        service.shutdown();
    }

    public static void main(String[] args) {

        var main = new Asyn();
        main.testAysn();
    }
}
