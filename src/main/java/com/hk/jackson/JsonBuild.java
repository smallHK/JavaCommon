package com.hk.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class JsonBuild {

    private String buildJson() {

        JsonFactory factory = new JsonFactory();
        StringWriter writer = new StringWriter();
        try {
            JsonGenerator generator = factory.createGenerator(writer);
            generator.writeStartObject();

            generator.writeNumberField("max", 0);

            generator.writeObjectFieldStart("private_data");
            generator.writeStringField("TYPE", "CC");
            generator.writeEndObject();

            generator.writeNumberField("cpu", 0);
            generator.writeEndObject();

            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Json build failed!");
            System.exit(1);
        }

        return writer.toString();
    }


    //构建json
    private static void buildJsonByTree() {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode node = factory.objectNode();
        node.put("aaa", 123);
        node.with("student").put("age", 17);
        ObjectMapper mapper = new ObjectMapper();

        try {
            System.out.println(mapper.writeValueAsString(node));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    //使用map构建json
    private static void buildJsonByMap() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("data", "123");
        jsonMap.put("ttt", null);
        try {
            String result = mapper.writeValueAsString(jsonMap);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        buildJsonByMap();
//        buildJsonByTree();
    }
}
