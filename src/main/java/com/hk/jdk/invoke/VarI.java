package com.hk.jdk.invoke;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarI {


    //新建数组元素VarHandle，执行CAS
    private static void firstVarHandle() {
        String[] sa = {"aaaa", "bbb", "ttt"};
        VarHandle avh = MethodHandles.arrayElementVarHandle(String[].class);
        boolean r = avh.compareAndSet(sa, 0, "aaa", "ttt");
        System.out.println(r);
        r = avh.compareAndSet(sa, 0, "aaaa", "new");
        System.out.println(r);
    }



    public static void main(String[] args) {
        firstVarHandle();
    }
}
