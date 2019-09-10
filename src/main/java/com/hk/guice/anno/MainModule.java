package com.hk.guice.anno;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(I.class).annotatedWith(A.class).to(Aimpl.class);


        bind(I.class).annotatedWith(Names.named("b")).to(Bimpl.class);
    }
}
