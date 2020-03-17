package com.hk.jdk.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Select {



    private static void firstServerChannel() {
        try {
            ServerSocketChannel ch = ServerSocketChannel.open();
            ch.bind(new InetSocketAddress(8080));
            System.out.println("完成绑定");
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

        firstServerChannel();
    }
}
