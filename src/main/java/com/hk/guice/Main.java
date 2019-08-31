package com.hk.guice;

import com.google.inject.Guice;
import com.google.inject.Provider;

import javax.inject.Inject;


public class Main {

    interface Des extends Runnable {
        void run ();
    }

    static class Hello implements Des{

        Provider<String> provider;

        @Inject
        public Hello(Provider<String> provider) {
            this.provider = provider;

        }

        @Override
        public void run() {
            System.out.println(provider.get());
        }
    }

    @Inject
    private Main(Des des) {
        this.des = des;
    }

    private Des des;

    private void boot() {
        des.run();
    }




    public static void main(String[] args) {

        var app = Guice.createInjector(new MainModule()).getInstance(Main.class);
        app.boot();
    }
}
