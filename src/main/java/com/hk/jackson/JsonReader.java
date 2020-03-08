package com.hk.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class JsonReader {


    //从json文件构建map
    private void gainMapFromJsonFile() {
        File file = new File(System.getProperty("user.dir") + File.separator + "vp.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map result = mapper.readValue(file, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
        ObjectNode mRoot = (ObjectNode) root;
        mRoot.put("gps", 1.44444);
        mRoot.put("data", new Date().getTime());

        try {
            System.out.println(mapper.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    @Getter
    @Setter
    static class RawJsonBind {

        public String name;

        public ObjectNode json;
    }


    /**
     * 解析嵌套对象
     */
    private static void parseJsonBind() {
        ObjectMapper mapper = new ObjectMapper();

        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode node = factory.objectNode();
        node.put("name", "test");
        node.with("json").put("test", "123");

        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(node);
            System.out.println(jsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            RawJsonBind bean = mapper.readValue(jsonStr, RawJsonBind.class);
//            System.out.println(bean.getJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        var main = new JsonReader();
//        main.beautyJson();
        //main.parseJsonStr();
//        main.modifyJsonStr();

        parseJsonBind();
    }
}
