package com.hk.jdk.security;

import java.io.FilePermission;

public class Per {


    /**
     * permission implies使用
     */
    private void firstPermission() {
        FilePermission fp = new FilePermission("C:\\Users\\dell\\Desktop\\cache\\corrupted\\*", "write");
        FilePermission ffp = new FilePermission("C:\\Users\\dell\\Desktop\\cache\\corrupted\\*", "write,execute");
        System.out.println(ffp.implies(fp));
    }

    public static void main(String[] args) {
        var p = new Per();
        p.firstPermission();
    }
}
