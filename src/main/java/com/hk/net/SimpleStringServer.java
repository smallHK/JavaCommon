package com.hk.net;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleStringServer {

    private void run() {

        int port = 8099;
        try (var server = new ServerSocket();) {

            server.bind(new InetSocketAddress(port));
            System.out.println("listen port: " + port);
            System.out.println();

            while (!server.isClosed()){
                Socket socket = server.accept();
                System.out.println("client address: " + socket.getInetAddress());
                System.out.println();
                var br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                var wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                wr.write("Hello!");
                wr.newLine();
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var server = new SimpleStringServer();
        server.run();
    }
}
