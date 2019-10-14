package com.hk.time;

import java.time.Duration;

public class Main {


    //解析时间段
    private static void parseDuration() {

        //一年的毫秒秒数
        System.out.println(Duration.parse("P365D").toMillis());


        //打印一个月
        System.out.println(Duration.parse("P30D").toString());
    }

    public static void main(String[] args) {

        parseDuration();
    }
}
