package com.hk;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Service {

    private UUID uuid = UUID.randomUUID();

    public void run() {
        System.out.println("Hello World!");
    }

    public void printId() {
        System.out.println("id: " + uuid.toString());
    }

}
