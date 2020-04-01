package com.hk.jdk.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsynNetChan {


    //异步组
    private static void asynGroup() {
        try {
            AsynchronousChannelGroup group = AsynchronousChannelGroup.withFixedThreadPool(4, Executors.defaultThreadFactory());
            AsynchronousServerSocketChannel ssch = AsynchronousServerSocketChannel.open(group);
            AsynchronousSocketChannel sch = AsynchronousSocketChannel.open(group);
            ssch.bind(new InetSocketAddress("127.0.0.1", 8080));
            ssch.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    result.read(buffer, 0, TimeUnit.MILLISECONDS, null, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            List<Byte> bs = new ArrayList<>();
                            buffer.flip();
                            while (buffer.position() < buffer.limit()) {
                                bs.add(buffer.get());
                            }
                            byte[] bytes = new byte[bs.size()];
                            for (int i = 0; i < bs.size(); i++) {
                                bytes[i] = bs.get(i);
                            }
                            System.out.println(new String(bytes));
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {

                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });

            sch.connect(new InetSocketAddress("127.0.0.1", 8080), null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    System.out.println("成功连接！");
                    sch.write(ByteBuffer.wrap("Hello World!".getBytes()));
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });

            System.out.println(group.isShutdown());
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            group.shutdownNow();
            System.out.println(group.isShutdown());
            try {
                group.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("结束！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //异步连接
    private static void asynChanCon() {
        try {
            AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
            ch.connect(new InetSocketAddress("127.0.0.1", 8080), null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    System.out.println("成功连接！");
                    ch.write(ByteBuffer.wrap("Hello World!".getBytes()));
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //bind
    private static void asynChanBind() {
        try {
            AsynchronousServerSocketChannel sch = AsynchronousServerSocketChannel.open();
            sch.bind(new InetSocketAddress("127.0.0.1", 8080));
            System.out.println("成功绑定" + sch.getLocalAddress());

            sch.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    result.read(buffer, 0, TimeUnit.MILLISECONDS, null, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            List<Byte> bs = new ArrayList<>();
                            buffer.flip();
                            while (buffer.position() < buffer.limit()) {
                                bs.add(buffer.get());
                            }
                            byte[] bytes = new byte[bs.size()];
                            for (int i = 0; i < bs.size(); i++) {
                                bytes[i] = bs.get(i);
                            }
                            System.out.println(new String(bytes));
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {

                        }
                    });

                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

//        asynChanBind();
//        asynChanCon();
//
//        while (true) {
//
//            try {
//                Thread.sleep(3 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        asynGroup();
    }
}
