package com.hk.http;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleClient {

    //http client发送form表单数据
    private void postFromData() {

        List<NameValuePair> nvps = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("max_fps", 90);
        CloseableHttpClient client = HttpClients.custom().build();
        HttpPost post = new HttpPost("localhost:8080");

        for (String key : params.keySet()) {
            String value = String.valueOf(params.get(key));
            nvps.add(new BasicNameValuePair(key, value));

        }
        post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
        try {
            CloseableHttpResponse response = client.execute(post);

            int code = response.getStatusLine().getStatusCode();
            if (code != 200) {
                System.out.println(code + ": " + response.getStatusLine().getReasonPhrase());
            }
            if (response.getEntity() != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = null;
                if ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            System.out.println("Http post failed!");
        }

    }


    private volatile boolean shutdown = false;

    //http client线程池
    private void poolClient() {
        var cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        cm.setMaxPerRoute(new HttpRoute(new HttpHost("localhost", 8081)), 50);


        int count = 0;
        while (!shutdown) {

            System.out.println("Loop count:" + count++);
            var client = HttpClients.custom().setConnectionManager(cm).build();
            var post = new HttpPost("http://localhost:8081");
            var config = RequestConfig.custom()
                    .setConnectionRequestTimeout(5 * 1000)
                    .setConnectTimeout(5 * 1000)
                    .setSocketTimeout(5 * 1000)
                    .build();
            post.setConfig(config);

            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("max_fps", String.valueOf(90)));
            post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
            try (var response = client.execute(post)) {
                if (response.getEntity() != null) {
                    var reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String line;
                    if ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println();
        }

    }

    public static void main(String[] args) {

        var main = new SimpleClient();
        main.poolClient();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            main.shutdown = true;
        }));

    }


}
