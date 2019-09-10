package com.hk.guice.anno;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Named;

public class Main {

    private I i;

//    @Inject
//    public Main(@A I i) {
//        this.i = i;
//    }

    @Inject
    public Main(@Named("b") I i ) {
        this.i = i;
    }

    public void run() {
        i.run();
    }
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MainModule());
        Main main = injector.getInstance(Main.class);
        main.run();
    }





}
