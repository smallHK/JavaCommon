package com.hk.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.logging.LoggingHandler;


//本地传输处理
public class LocalTransport {

    private EventLoopGroup eventLoopGroup = new DefaultEventLoopGroup(3);

    void server() {

        LocalAddress address = new LocalAddress("ll");


        ServerBootstrap sb = new ServerBootstrap();
        sb.group(eventLoopGroup)
                .channel(LocalServerChannel.class)
                .handler(new ChannelInitializer<LocalServerChannel>() {
                    @Override
                    protected void initChannel(LocalServerChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                    }
                })
                .childHandler(new ChannelInitializer<LocalChannel>() {
                    @Override
                    protected void initChannel(LocalChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new LoggingHandler(),
                                new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("服务器读被触发");
                                        System.out.println(msg);
                                        ctx.write("Give you serve!");
                                    }

                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("服务器成功连接！");
                                    }
                                }
                        );
                    }
                });

        try {
            sb.bind(address).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("完成服务器初始化");


    }

    void client() {
        LocalAddress address = new LocalAddress("ll");

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap cb = new Bootstrap();
            cb.group(group)
                    .channel(LocalChannel.class)
                    .handler(new ChannelInitializer<LocalChannel>() {
                        @Override
                        protected void initChannel(LocalChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(),
                                    new SimpleChannelInboundHandler() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println(msg);
                                        }
                                    });

                        }
                    });

            try {
                Channel ch = cb.connect(address).sync().channel();

                ChannelFuture ff = ch.writeAndFlush("HAAAAA!");
                if (ff != null) {
                    ff.awaitUninterruptibly();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } finally {
            group.shutdownGracefully();
        }
    }

    void run() {
        server();
        client();
        eventLoopGroup.shutdownGracefully();
    }


    //本地收发
    public static void main(String[] args) {

        var main = new LocalTransport();
        main.run();
    }
}
