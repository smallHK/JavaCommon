package com.hk.netty;

import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;

import static io.netty.buffer.Unpooled.buffer;
import static io.netty.buffer.Unpooled.copiedBuffer;

public class Buffer {


    //段操作
    private static void firstSegmentOperation() {

        ByteBuf bf = buffer();
        System.out.println(bf.readerIndex());
        System.out.println(bf.writerIndex());
        System.out.println(bf.capacity());
        bf.writeCharSequence("haha", StandardCharsets.UTF_8);
        System.out.println(bf.readerIndex());
        System.out.println(bf.writerIndex());
        System.out.println(bf.capacity());

        ByteBuf rf = buffer();
        bf.readBytes(rf, 2);
        System.out.println(bf.readerIndex());
        System.out.println(bf.writerIndex());
        System.out.println(bf.capacity());

        System.out.println(rf.getCharSequence(0, 2, StandardCharsets.UTF_8));

    }

    private static void firstCreateByteBuf() {
        ByteBuf bf = copiedBuffer("haha", StandardCharsets.UTF_8);
        System.out.println(bf.getByte(1));
    }

    public static void main(String[] args) {
//        firstCreateByteBuf();
        firstSegmentOperation();
    }
}
