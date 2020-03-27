package com.hk.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.StandardCharsets;

public class MessageT {


    //构建HTTP请求
    private static void buildHttpRequest() {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "http://127.0.0.1/hello");
        ByteBuf content = request.content();
        content.writeCharSequence("Hello World!", StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {

    }
}
