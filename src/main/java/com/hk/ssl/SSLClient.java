package com.hk.ssl;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

//未成功
public class SSLClient {

    private SSLSocket socket = null;

    private SSLClient() throws IOException {
        var socketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        socket = (SSLSocket)socketFactory.createSocket("localhost", 7070);
    }

    private void connect() {
        try(var pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));){

            var userName  = "principal";
            pw.println(userName);
            var password = "credential";
            pw.println(password);
            pw.flush();

            try(var br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                var response = br.readLine();
                response += "\n" + br.readLine();
                System.out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);

    }

    public static void main(String[] args) {
        try {
            new SSLClient().connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
