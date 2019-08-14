package com.hk.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpStatus;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SimpleServer {

    private SocketConfig socketConfig = SocketConfig.custom()
            .setSoTimeout(15000)
            .setTcpNoDelay(true)
            .build();

    private HttpRequestHandler handler = (request, response, context) -> {

        System.out.println("Accept Request!");
        HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();

        try(var is = entity.getContent()) {
            String result = new String(is.readAllBytes());
            System.out.println(result);
        }

        System.out.println();
        //完成请求
        response.setEntity(new StringEntity("Got it!"));
        response.setStatusCode(HttpStatus.SC_OK);
    };

    private HttpProcessor processor = HttpProcessorBuilder.create().build();

    private final HttpServer server = ServerBootstrap.bootstrap()
            .setListenerPort(8080)
            .setHttpProcessor(processor)
            .setSocketConfig(socketConfig)
            .registerHandler("*", handler)
            .create();

    private void start() {
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server has started!");
        System.out.println();
        try {
            server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        var server = new SimpleServer();
        server.start();
    }
}
