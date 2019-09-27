package com.hk.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class JsonReader {

    private void beautyJson() {

        File file = new File(System.getProperty("user.dir") + File.separator + "vp.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            var json = mapper.readValue(file, Object.class);
            System.out.println(mapper.writeValueAsString(json));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //从字符串解析json
    private void parseJsonStr() {
        String data = "{\"type\":2,\"range\":1,\"start\":1368417600,\"end\":1368547140,"
                + "\"cityName\":\"天津\",\"companyIds\":[\"12000001\"],\"companyNames\":[\"天津\"],"
                + "\"12000001\":{\"data\":[47947,48328,48573,48520],"
                + "\"timestamps\":[1368417600,1368417900,1368418200,1368418500]}}";
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = null;
        try {
             root = mapper.readTree(data);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println(root.path("type").asInt());
    }

    //更改json
    private void modifyJsonStr() {

        String data = "{\"type\":2}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(data);
        } catch (IOException e) {
            System.exit(1);
        }
        ObjectNode mRoot = (ObjectNode)root;
        mRoot.put("gps", 1.44444);
        mRoot.put("data", new Date().getTime());

        try {
            System.out.println(mapper.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        var main = new JsonReader();
//        main.beautyJson();
        //main.parseJsonStr();
        main.modifyJsonStr();
    }
}
