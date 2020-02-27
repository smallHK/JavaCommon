package com.hk.jdk.ref;

import java.lang.ref.WeakReference;

public class RefT {

    //gc清理弱引用
    private void weakRef() {
        WeakReference<Object> ref = new WeakReference<>(new Object());
        System.out.println(ref.get());
        System.gc();
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ref.get());
    }

    public static void main(String[] args) {
        var ref = new RefT();
        ref.weakRef();
    }
}
