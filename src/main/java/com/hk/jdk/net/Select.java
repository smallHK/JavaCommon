package com.hk.jdk.net;

import io.lettuce.core.ScriptOutputType;
import net.jpountz.util.ByteBufferUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Select {

    //非阻塞客户端socket io
    private static void nonBlockingConnect() {

        try {
            SocketChannel ch = SocketChannel.open();
            ch.configureBlocking(false);
            System.out.println("connect:" + ch.connect(new InetSocketAddress(8080)));
            System.out.println("pend: " + ch.isConnectionPending());
            System.out.println("finish: " + ch.finishConnect());
            System.out.println("pend: " + ch.isConnectionPending());

            ByteBuffer bf = ByteBuffer.allocate(244);
            for(char c : "Hello World!".toCharArray()) {
                bf.putChar(c);
            }
            bf.flip();
            ch.write(bf);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //首个非阻塞IO
    private static void firstNonBlockingChannel() {
        try {
            ServerSocketChannel ch = ServerSocketChannel.open();
            System.out.println(ch.isBlocking());
            System.out.println(ch.isRegistered());

            ch.configureBlocking(false);
            System.out.println(ch.isBlocking());
            ch.bind(new InetSocketAddress(8080));

            new Thread(() -> {

                while (true) {
                    try {
                        SocketChannel sch = null;
                        if((sch = ch.accept()) != null) {
                            System.out.println("监听成功！");
                            ByteBuffer buf = ByteBuffer.allocate(254);
                            StringBuilder sb = new StringBuilder();
                            sch.read(buf);
                            buf.flip();
                            while (buf.position() < buf.limit()) {
                                sb.append(buf.getChar());
                            }
                            System.out.println(sb.toString());
                            break;
                        }else{
                            System.out.println("无连接！");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //客户端
    private static void firstChannel() {
        try {
            SocketChannel channel = SocketChannel.open();
            if(channel.connect(new InetSocketAddress(8080))) {
                System.out.println("客户端成功连接！");
                channel.write(ByteBuffer.wrap(new String("Hello world!").getBytes(Charset.forName("utf-8"))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //服务器
    private static void firstServerChannel() {
        try {
            ServerSocketChannel ch = ServerSocketChannel.open();
            ch.bind(new InetSocketAddress(8080));
            System.out.println("完成绑定");

            new Thread(() -> {
                SocketChannel socketChannel = null;
                try {
                    socketChannel = ch.accept(); //阻塞操作！
                    ByteBuffer bf = ByteBuffer.allocate(1000);
                    socketChannel.read(bf);
                    System.out.println( bf.position());
                    socketChannel.finishConnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void firstSelector() {
        try(Selector selector = Selector.open();) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

//        firstServerChannel();
//        firstChannel();

        firstNonBlockingChannel();
//        firstChannel();
        nonBlockingConnect();

    }
}
