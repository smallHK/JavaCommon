package com.hk.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import java.io.StringWriter;

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
}
