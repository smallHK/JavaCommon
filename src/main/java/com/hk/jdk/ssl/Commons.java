package com.hk.jdk.ssl;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

/**
 * @author smallHK
 * 2020/4/4 10:55
 */
public class Commons {

    private static void keyManagerFactory() {
        System.out.println(KeyManagerFactory.getDefaultAlgorithm());
        try {
            KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            System.out.println(factory.hashCode());
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(null, null);
            factory.init(ks, null);
            System.out.println(factory.getKeyManagers().length);
            for (KeyManager km: factory.getKeyManagers()) {
                System.out.println(km.hashCode());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
    }

    private static void printSessionInfo() {
        SocketFactory factory = SSLSocketFactory.getDefault();
        try {
            SSLSocket cl = (SSLSocket) factory.createSocket();
            cl.connect(new InetSocketAddress(8080));
            System.out.println("客户端： " + cl.getSSLParameters().getEndpointIdentificationAlgorithm());
//            cl.startHandshake();
//            SSLSession session = cl.getSession();
//            System.out.println("客户端： " + session.getProtocol());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void printParameter() {
        SocketFactory factory = SSLSocketFactory.getDefault();
        try {
            SSLSocket cl = (SSLSocket) factory.createSocket();
            cl.connect(new InetSocketAddress(8080));
            SSLParameters paras = cl.getSSLParameters();
            System.out.println("客户端：" + paras.getMaximumPacketSize());
            System.out.println("客户端：" + paras.getNeedClientAuth());
            System.out.println("客户端 协议： " + Arrays.toString(paras.getProtocols()));
            for (String pro: paras.getProtocols()) {
                System.out.println("客户端 协议 unit： " + pro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void normalConnect() {
        SocketFactory factory = SSLSocketFactory.getDefault();
        try {
            SSLSocket cl = (SSLSocket) factory.createSocket();
            cl.connect(new InetSocketAddress(8080));
            System.out.println("客户端连接： " + cl.isConnected());
//            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(cl.getOutputStream()));
//            br.write("hello world!");
//            br.newLine();
//            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serverSocket() {
        ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
        try {
            SSLServerSocket server = (SSLServerSocket) factory.createServerSocket();
            server.bind(new InetSocketAddress(8080));
            System.out.println(server.hashCode());
            System.out.println(server.isBound());
            new Thread(() -> {
                System.out.println("启动监听");
                try {
                    SSLSocket socket = (SSLSocket) server.accept();
                    System.out.println("成功accept: " + socket.hashCode());
                    System.out.println("服务端： " + socket.getSSLParameters().getEndpointIdentificationAlgorithm());
//                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        System.out.println(line);
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void initSSLSocketFactory() {
        for(String cipher: ((SSLSocketFactory)SSLSocketFactory.getDefault()).getDefaultCipherSuites()) {
            System.out.println(cipher);
        }

        System.out.println();
        for(String cipher: ((SSLSocketFactory)SSLSocketFactory.getDefault()).getSupportedCipherSuites()) {
            System.out.println(cipher);
        }

    }

    private static void context() {
        try {
            SSLContext context = SSLContext.getInstance("SSLv3");
            System.out.println("create success!");

            context.init(null, null, null);
            SSLSocketFactory factory = context.getSocketFactory();
            System.out.println(factory.hashCode());
            SSLServerSocketFactory serverSocketFactory = context.getServerSocketFactory();
            System.out.println(serverSocketFactory.hashCode());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        context();

//        initSSLSocketFactory();

//        serverSocket();
//        normalConnect();
//        printParameter();
//        printSessionInfo();
//        keyManagerFactory();
    }
}
