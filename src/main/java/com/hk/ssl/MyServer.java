package com.hk.ssl;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class MyServer {

    public static void main(String[] args) throws Exception {

        KeyStore serverKeyStore = KeyStore.getInstance("JKS");
        serverKeyStore.load(new FileInputStream("e:\\myserver.jks"), "123456".toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(serverKeyStore, "123456".toCharArray());

        KeyStore clientKeyStore = KeyStore.getInstance("JKS");
        clientKeyStore.load(new FileInputStream(""), "123456".toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(clientKeyStore);


        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        var serverFactory = sslContext.getServerSocketFactory();

        SSLServerSocket svrSocket = (SSLServerSocket) serverFactory.createServerSocket(34567);


        var supported = svrSocket.getEnabledCipherSuites();
        svrSocket.setEnabledCipherSuites(supported);

        System.out.println("Accept!");

        SSLSocket cntSocket = (SSLSocket) svrSocket.accept();

        InputStream in = cntSocket.getInputStream();

        int a = in.read(new byte[102]);
        System.out.println("from client: " + a);

    }
}
