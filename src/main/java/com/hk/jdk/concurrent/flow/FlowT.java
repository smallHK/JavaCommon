package com.hk.jdk.concurrent.flow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowT {


    //首个订阅者
    static class FirstSubscriber implements Flow.Subscriber<String> {
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            System.out.println("sub");
        }
        @Override
        public void onNext(String item) {
            System.out.println("subscribe: " + item);
        }

        @Override
        public void onError(Throwable throwable) {

        }
        @Override
        public void onComplete() {
            System.out.println("com");
        }
    }

    public void trySub() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>(service, 1);
        publisher.subscribe(new FirstSubscriber());
        System.out.println(publisher.hasSubscribers());
        System.out.println(publisher.getMaxBufferCapacity());
        publisher.submit("Hello world!");
        publisher.submit("hhhh!");
        service.shutdown();
    }

    public static void main(String[] args) {
        var f = new FlowT();
        f.trySub();
    }


}
