package com.hk.jdk.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Select {

    //取消后注册
    private static void cancel() {
        try {
            SocketChannel ch = SocketChannel.open();
            ch.configureBlocking(false);
            Selector selector = Selector.open();
            SelectionKey key = ch.register(selector, ch.validOps());
            System.out.println(key.hashCode());
            key.cancel();
            selector.selectNow();
            key = ch.register(selector, ch.validOps());
            System.out.println(key.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //无阻塞连接建立，SelectionKey就绪状态变化
    private static void selectConnect() {

        try {
            Selector selector = Selector.open();
            SocketChannel client = SocketChannel.open();
            client.configureBlocking(false);

            Map<Integer, String> sMap = new HashMap<>();

            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(8080));
            SelectionKey clientKey = client.register(selector, client.validOps());
            SelectionKey serverKey = server.register(selector, server.validOps());
            System.out.println(serverKey.hashCode() + ":" + serverKey.isAcceptable());
            sMap.put(clientKey.hashCode(), "client");
            sMap.put(serverKey.hashCode(), "server");

            client.connect(new InetSocketAddress(8080));
            selector.selectNow();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            System.out.println("有效键： " + selector.selectedKeys().size());
            while (it.hasNext()) {
                SelectionKey key = it.next();
                System.out.println(sMap.get(key.hashCode()));
                System.out.println("可读： " + key.isReadable());
                System.out.println("可写： " + key.isWritable());
                System.out.println("可连接： " + key.isConnectable());
                System.out.println("可接受： " + key.isAcceptable());
                System.out.println();
                it.remove();
            }
            System.out.println("=============");


            SocketChannel ch = server.accept();
            selector.selectNow();
            it = selector.selectedKeys().iterator();
            System.out.println("有效键： " + selector.selectedKeys().size());
            while (it.hasNext()) {
                SelectionKey key = it.next();
                System.out.println(sMap.get(key.hashCode()));
                System.out.println("可读： " + key.isReadable());
                System.out.println("可写： " + key.isWritable());
                System.out.println("可连接： " + key.isConnectable());
                System.out.println("可接受： " + key.isAcceptable());
                System.out.println();
                it.remove();
            }
            System.out.println("=============");


            client.finishConnect();
            ch.configureBlocking(false);
            System.out.println(ch.hashCode());
            ch.register(selector, ch.validOps());
            selector.selectNow();
            it = selector.selectedKeys().iterator();
            System.out.println("有效键： " + selector.selectedKeys().size());
            while (it.hasNext()) {
                SelectionKey key = it.next();
                System.out.println(sMap.get(key.hashCode()));
                System.out.println("可读： " + key.isReadable());
                System.out.println("可写： " + key.isWritable());
                System.out.println("可连接： " + key.isConnectable());
                System.out.println("可接受： " + key.isAcceptable());
                System.out.println();
                it.remove();
            }
            System.out.println("=============");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void selectOpt() {
        try {
            Selector selector = Selector.open();

            ServerSocketChannel serverCh = ServerSocketChannel.open();
            serverCh.bind(new InetSocketAddress(8080));
            serverCh.configureBlocking(false);
            SelectionKey key = serverCh.register(selector, serverCh.validOps());

            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println("执行选择操作： " + selector.selectNow());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("就绪集合： " + selector.selectedKeys().size());
                    if (key.isAcceptable()) {
                        System.out.println("存在可接收连接！");
                        break;
                    }
                    System.out.println("就绪集合： " + selector.selectedKeys().size());
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //serverChannel注册
    private static void firstSelectorKey() {
        try {
            Selector selector = Selector.open();

            ServerSocketChannel serverCh = ServerSocketChannel.open();
            serverCh.bind(new InetSocketAddress(8080));
            serverCh.configureBlocking(false);
            SelectionKey key = serverCh.register(selector, serverCh.validOps());
            System.out.println(key.isValid());

            System.out.println(key.interestOps());
            System.out.println((key.interestOps() & SelectionKey.OP_ACCEPT) != 0);
            System.out.println((key.interestOps() & SelectionKey.OP_CONNECT) != 0);
            System.out.println((key.interestOps() & SelectionKey.OP_READ) != 0);
            System.out.println((key.interestOps() & SelectionKey.OP_WRITE) != 0);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


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

//        firstNonBlockingChannel();
//        firstChannel();
//        nonBlockingConnect();

//        firstSelectorKey();

//        selectOpt();
//        try {
//            Thread.sleep(3 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        firstChannel();
//        selectConnect();

        cancel();
    }

}
