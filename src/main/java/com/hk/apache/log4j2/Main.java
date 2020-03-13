package com.hk.apache.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static {
        System.out.println( System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j2.properties");
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j2.properties");
    }
    private static final Logger logger = LogManager.getLogger(Main.class.getName());


    private static void firstLayout() {
        logger.info("Hello world!");

        ExecutorService service = Executors.newFixedThreadPool(2);

        for(int i = 0;i < 10; i++) {
            service.submit(() -> {
                logger.info("This is Thread");
            });
        }

        service.shutdown();
    }


    public static void main(String[] args) {
        firstLayout();
    }
}
