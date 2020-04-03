package com.hk.jdk.invoke;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarI {

    //lookup创建字段的VarHandle
    private static void findVarHandle() {

        class C {
            private int c = 9;
        }

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            VarHandle vh = lookup.findVarHandle(C.class, "c", int.class);
            C test = new C();
            System.out.println(test.c);
            System.out.println(vh.compareAndSet(test, 9, 12));
            System.out.println(test.c);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


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
//        firstVarHandle();
        findVarHandle();
    }
}
