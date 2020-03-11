package com.hk.apache.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Main {

    static {
        System.out.println( System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j2.properties");
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j2.properties");
    }
    private static final Logger logger = LogManager.getLogger(Main.class.getName());


    private void stop() {
        logger.info("Bybe!");
    }

    public static void main(String[] args) throws InterruptedException {

        var main = new Main();
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            main.stop();
        }));

        for (int i = 0; i < 10; i ++) {
            System.out.println(1);
            Thread.sleep(2 * 1000);
        }

    }
}
