package com.hk.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class FirstPol {


    public static class B {
        long f;
    }


    private static void alignment () {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(B.class).toPrintable());
    }


    public static class A {
        boolean f;
    }

    private static void firstParse() {
        System.out.println("first test!");
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(A.class).toPrintable());
    }

    public static void main(String[] args) {
        firstParse();
        alignment();
    }
}
