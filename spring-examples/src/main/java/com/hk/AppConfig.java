package com.hk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean("sin")
    public Service firstBean() {
        return new Service();
    }

    @Bean("sin2")
    public Service secondBean() {
        return new Service();
    }

    @Bean("pro")
    @Scope("prototype")
    public Service prototypeBean() {
        return new Service();
    }
}
