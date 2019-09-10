package com.hk.guice.anno;

public class Bimpl implements I {
    @Override
    public void run() {
        System.out.println("This is B impl!");
    }
}
