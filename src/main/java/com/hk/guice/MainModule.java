package com.hk.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Main.Des.class).to(Main.Hello.class);
    }

    @Provides private String getString() {
        return "Hello World!";
    }
}
