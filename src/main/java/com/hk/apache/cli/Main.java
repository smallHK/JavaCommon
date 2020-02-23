package com.hk.apache.cli;

import org.apache.commons.cli.*;

public class Main {

    //-t [task_name] 处理
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("t", true, "执行的任务");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("t")) {
                System.out.println(cmd.getOptionValue("t"));
            }

        } catch (ParseException e) {
            System.out.println("Parsing failed! " + e.getMessage());
        }
    }
}
