package com.hk.jdk.ssl;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;

//未成功
public class SSLServer {

    private static final String USER_NAME = "principal";

    private static final String PASSWORD = "credential";

    private static final String SECRET_CONTENT =
            "This is confidential content from server X, for your eye!";

    private SSLServerSocket serverSocket;

    private SSLServer() {
        var socketFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        try {
            serverSocket = (SSLServerSocket)socketFactory.createServerSocket(7070);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runServer() {

        while (true) {
            System.out.println("Waiting for connection...");

            try(var socket = (SSLSocket)serverSocket.accept();
                var br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                var pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));){
                var userName = br.readLine();
                var password = br.readLine();

                if(userName.equals(USER_NAME) && password.equals(PASSWORD)) {
                    pw.println("Welcome, " + userName);
                    pw.println(SECRET_CONTENT);
                }else {
                    pw.println("Authentication failed!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        var server = new SSLServer();
        server.runServer();
    }
}
