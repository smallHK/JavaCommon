package com.hk.reactor;

import reactor.core.publisher.Flux;

public class First {


    public static void firstFlux() {
        Flux<String> seq = Flux.just("fff", "ttt", "kkk");
        seq.subscribe(System.out::println);
    }

    public static void main(String[] args) {
        firstFlux();
    }

}
