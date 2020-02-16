package com.hk.time;

import java.time.Duration;
import java.time.Instant;

public class Main {



    private static void gainNowTimeStamp() {
        System.out.println(Instant.now().getEpochSecond());
    }

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
