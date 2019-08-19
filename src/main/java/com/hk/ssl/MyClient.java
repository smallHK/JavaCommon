package com.hk.ssl;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

public class MyClient {

    public static void main(String[] args) throws Exception {
        var clientKeyStore = KeyStore.getInstance("JKS");
        clientKeyStore.load(new FileInputStream(""), "".toCharArray());

        var kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(clientKeyStore, "".toCharArray());

        var serverKeyStore = KeyStore.getInstance("JKS");
        serverKeyStore.load(new FileInputStream(""), "".toCharArray());

        var tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(serverKeyStore);

        var  sslContext = SSLContext.getInstance("SSL");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        var sslcntFactory = (SSLSocketFactory)sslContext.getSocketFactory();
        var sslSocket = (SSLSocket)sslcntFactory.createSocket("127.0.0.1", 34567);

        var supported = sslSocket.getSupportedCipherSuites();
        sslSocket.setEnabledCipherSuites(supported);

        var out = sslSocket.getOutputStream();
        out.write("hello".getBytes());
    }
}
