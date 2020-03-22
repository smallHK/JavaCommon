package com.hk.jdk.buf;

import java.nio.ByteBuffer;

public class Main {


    //buffer读写
    private static void byteBuffer() {

        ByteBuffer buffer = ByteBuffer.allocate(200);
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());

        String s = "Hello World!";
        for(char c : s.toCharArray()) {
            buffer.putChar(c);
        }
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());

        buffer.flip();
        StringBuilder sb = new StringBuilder();
        while(buffer.position() < buffer.limit()) {
            char cc = buffer.getChar();
            sb.append(cc);
        }
        System.out.println(sb.toString());
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());

        buffer.clear();
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());
        for(char c : s.toCharArray()) {
            buffer.putChar(c);
        }
        System.out.println(buffer.position());
        System.out.println(buffer.capacity());
        System.out.println(buffer.limit());
    }

    public static void main(String[] args) {
        byteBuffer();
    }
}
