package com.hk.jdk.tools;

import com.sun.tools.javac.Main;

import java.io.File;

public class Compiler {

    //java旧版jdk javac编程接口
    public static void main(String[] args) {
        String s = System.getProperty("user.dir") + File.separator + "src" +
        File.separator + "main" +
                File.separator + "java" +
                File.separator + "com" +
                File.separator + "hk" +
                File.separator + "jdk" +
                File.separator + "tools" +
                File.separator + "Compiler.java" + " -d " + System.getProperty("user.dir") + File.separator + "cache";
        Main.compile(s.split(" "));
    }
}
