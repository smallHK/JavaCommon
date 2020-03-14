package com.hk.netty;

import io.netty.channel.Channel;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ChannelFactoryT {

    private static void firstChannel() {
        ReflectiveChannelFactory<NioServerSocketChannel> factory = new ReflectiveChannelFactory<>(NioServerSocketChannel.class);
        Channel channel = factory.newChannel();
        System.out.println(channel.isOpen());
        System.out.println(channel.isActive());
        System.out.println(channel.isRegistered());
    }

    public static void main(String[] args) {
        firstChannel();
    }
}
