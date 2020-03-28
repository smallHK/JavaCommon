package com.hk.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class MessageT {


    //构建HTTP请求
    private static void buildHttpRequest() {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "http://127.0.0.1/hello");
        ByteBuf content = request.content();
        content.writeCharSequence("Hello World!", StandardCharsets.UTF_8);
    }

    //构建Http响应
    private static void buildHttpResponse() {
        ByteBuf content = Unpooled.buffer(0);
        content.writeCharSequence("This is server!", StandardCharsets.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
    }

    public static void main(String[] args) {

    }
}
