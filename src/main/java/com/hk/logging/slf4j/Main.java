package com.hk.logging.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {

    static {
        System.out.println(System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j2.properties");
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j2.properties");
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Hello World!");
    }
}
