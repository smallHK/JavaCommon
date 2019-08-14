package com.hk.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

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

    public static void main(String[] args) {

        var main = new JsonReader();
        main.beautyJson();
    }
}
