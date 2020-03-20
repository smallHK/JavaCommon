package com.hk.netty;

import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;
import static io.netty.buffer.Unpooled.copiedBuffer;

public class Buffer {

    private static void firstCreateByteBuf() {
        ByteBuf bf = copiedBuffer("haha", StandardCharsets.UTF_8);
        System.out.println(bf.getByte(1));
    }

    public static void main(String[] args) {
        firstCreateByteBuf();
    }
}
