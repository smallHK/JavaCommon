package com.hk.redis;

import io.lettuce.core.RedisClient;

import java.util.ArrayList;
import java.util.List;

public class Client {


    private void simpleClient() {
        var client = RedisClient.create("redis://yisa123456q@192.168.0.74:6495");
        var connection = client.connect();
        var command = connection.sync();

        command.keys("*");
        command.set("lettuce", "Hello, Redis!");
        List<String> ar = new ArrayList<>();
        command.lpush("", ar.toArray(new String[0]));
        connection.close();
        client.shutdown();
    }

    private void jedisClient() {


    }

    public static void main(String[] args) {
        var client = new Client();
        client.simpleClient();
    }

}
