package com.hk.jdk.concurrent;

import java.util.concurrent.*;

public class Asyn {

    private static void asynTask() {
        Runnable r = () -> {
            System.out.println("This is a task!");
        };
        FutureTask<String> task = new FutureTask<>(r, "Hello World!");
        System.out.println(task.isDone());
        task.run();
        System.out.println(task.isDone());
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static void asynExecutor() {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletionService<Integer> service = new ExecutorCompletionService<>(executor);
        class Task implements Callable<Integer> {
            private int num;

            private Task(int num) {
                this.num = num;
            }

            @Override
            public Integer call() throws Exception {
                System.out.println("执行任务： " + num);
                return num;
            }
        }
        for(int i = 0; i < 10; i++) {
            System.out.println("提交任务： " + i);
            service.submit(new Task(i));
        }

        System.out.println("xxxx");

        executor.shutdown();

        for(int i = 0; i < 10; i++) {
            Future<Integer> future = null;
            try {
                future = service.take();
                System.out.println("任务: " + future.get() + " 完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

    }

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
//        main.testAysn();
//        asynExecutor();
        asynTask();
    }
}
