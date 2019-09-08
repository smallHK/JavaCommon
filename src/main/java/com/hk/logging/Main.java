package com.hk.logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author smallHK
 * 2019/9/8 9:35
 */
public class Main {

    //root的Handler
    private void printRootLogging() {
        Logger logger = Logger.getLogger("");
        Handler[] handlers = logger.getHandlers();
        for (Handler handler: handlers) {
            System.out.println("handler : " + handler);
            System.out.println("level: " + handler.getLevel());
            System.out.println("formatter: " + handler.getFormatter());
        }
    }

    //修改配置
    private void logDependsSpec() {
        Handler fh = null;
        try {
            fh = new FileHandler(System.getProperty("user.dir") + File.separator + "out.log");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Logger.getLogger("").addHandler(fh);
        Logger.getLogger("hk.logging").setLevel(Level.FINEST);
        Logger logger = Logger.getLogger("hk.logging");
        logger.info("doing stuff");
        logger.finest("doing stuff");
    }

    //依赖基于配置文件的LogManager
    private void logDependsRoot() {

        Logger logger = Logger.getLogger("hk.logging");
        logger.fine("doing stuff");

        try {
            throw new Exception();
        } catch (Exception e) {
            logger.log(Level.WARNING, "trouble sneezing");
        }


        logger.fine("done");

    }


    public static void main(String[] args) {

        var app = new Main();
//        app.logDependsRoot();
//        app.logDependsSpec();
        app.printRootLogging();
    }
}
