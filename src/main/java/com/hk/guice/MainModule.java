package com.hk.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import jdk.jfr.Name;

import java.io.PrintStream;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Main.Des.class).to(Main.Hello.class);

        //实例绑定
        bind(PrintStream.class).annotatedWith(Names.named("ibi")).toInstance(System.out);

        //提供者绑定
        bind(Main.ProviderI.class).toProvider(Main.ProviderBindingProvider.class);

        //构造器注入
        try {
            bind(Main.CI.class).toConstructor(Main.ConstructorBinding.class.getConstructor());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Provides private String getString() {
        return "Hello World!";
    }


    //提供者方法
    @Provides @Named("pme")
    private String pmeStr() {
        return "pme";
    }

    @Provides
    @Named("pm")
    private String pmStr(@Named("pme") String pme) {
        return "pm" + ":" + pme;
    }
}
