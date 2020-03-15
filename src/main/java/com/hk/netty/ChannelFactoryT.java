package com.hk.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class ChannelFactoryT {


    /**
     * nio server通道注册事件循环，执行端口绑定
     */
    private static void firstRegister() {
        ReflectiveChannelFactory<NioServerSocketChannel> factory = new ReflectiveChannelFactory<>(NioServerSocketChannel.class);
        Channel channel = factory.newChannel();

        System.out.println(channel.isOpen());
        System.out.println(channel.isActive());
        System.out.println(channel.isRegistered());
        System.out.println();

        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture cf = group.register(channel);
        cf.addListener(f -> {
            System.out.println(channel.isOpen());
            System.out.println(channel.isActive());
            System.out.println(channel.isRegistered());
            System.out.println();

            ChannelFuture bcf = channel.bind(new InetSocketAddress("127.0.0.1", 8080));
            bcf.addListener(fb -> { //回调地狱？
                System.out.println(channel.isOpen());
                System.out.println(channel.isActive());
                System.out.println(channel.isRegistered());
                System.out.println("完成绑定！");
            });
            System.out.println("绑定端口！");

        });
    }

    private static void firstChannel() {
        ReflectiveChannelFactory<NioServerSocketChannel> factory = new ReflectiveChannelFactory<>(NioServerSocketChannel.class);
        Channel channel = factory.newChannel();
        System.out.println(channel.isOpen());
        System.out.println(channel.isActive());
        System.out.println(channel.isRegistered());
        System.out.println();


    }

    public static void main(String[] args) {
//        firstChannel();
        firstRegister();
    }
}
