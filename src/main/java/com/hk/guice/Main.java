package com.hk.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.ProvisionException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintStream;


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



    //实例绑定
    static class InstanceBinding {

        private PrintStream ps;

        @Inject
        public InstanceBinding(@Named("ibi") PrintStream ps) {
            this.ps = ps;
        }

        void run() {
            ps.println("AHHH!");
        }

    }

    //提供者方法绑定
    static class ProvidesMethods
    {
        private String str;
        @Inject
        public ProvidesMethods(@Named("pm") String str) {
            this.str = str;
        }

        void run() {
            System.out.println(str);
        }
    }


    interface ProviderI extends Runnable {
    }

    //提供者绑定
    @Named("pb")
    static class ProviderBindingProvider implements Provider<ProviderI> {

        @Override
        public ProviderI get() {
            return () -> {
                System.out.println("Provider!");
            };
        }
    }

    static class ProviderBinding {
        private ProviderI i;
        @Inject
        ProviderBinding(ProviderI i) {
            this.i = i;
        }
        void run() {
            i.run();
        }
    }


    //构造器绑定
    interface CI extends Runnable {}

    static class ConstructorBinding implements CI {

        public ConstructorBinding() {

        }
        @Override
        public void run() {
            System.out.println("ConstructorBinding!");
        }
    }

    static class CBService {
        CI ci;
        @Inject
        public CBService(CI ci) {
            this.ci = ci;
        }

         void run() {
            ci.run();
        }
    }

    public static void main(String[] args) {

        Injector injector= Guice.createInjector(new MainModule());
        var app = injector.getInstance(Main.class);
        app.boot();

        var ibt = injector.getInstance(Main.InstanceBinding.class);
        ibt.run();

        var pm = injector.getInstance(Main.ProvidesMethods.class);
        pm.run();

        var pb = injector.getInstance(ProviderBinding.class);
        pb.run();

        var cb = injector.getInstance(CBService.class);
        cb.run();


    }
}
