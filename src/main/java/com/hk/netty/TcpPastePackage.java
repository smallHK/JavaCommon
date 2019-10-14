package com.hk.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

//手动引发TCP粘包
public class TcpPastePackage {


    class TimeServerHandler extends ChannelInboundHandlerAdapter {

        private int counter;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {

        }
    }

    private void server() throws Exception {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();

        try {

            b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    });

            //同步等待成功
            ChannelFuture f = b.bind(8080).sync();

            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }


    }

    private void client() throws  Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();

            ChannelFuture f = b.connect().sync();

        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {


        var test = new TcpPastePackage();

        //启动服务器
        try {
            test.server();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //sleep
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动客户端
        try {
            test.client();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
