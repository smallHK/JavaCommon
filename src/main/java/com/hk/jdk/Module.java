package com.hk.jdk;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;


public class Module {
    public static void main(String[] args) {
        ModuleLayer ml = ModuleLayer.boot();

        Configuration c = ml.configuration();

        ResolvedModule m = c.findModule("VisualApp").get();
        ModuleReference r = m.reference();

        try(ModuleReader mr = r.open();) {

            mr.list().filter(e -> e.endsWith(".class")).filter(e -> !e.equals("module-info.class")).map(e -> {
                String s = e.replace('/', '.');
                return  s.replace(".class", "");
            }).forEach(e -> {
                try {
                    Class clazz = Class.forName(e);
                    System.out.println(clazz.toGenericString());
                    System.out.println(clazz.getClassLoader().getName());
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
