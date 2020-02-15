package com.hk.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Main {


    private void produce() {

    }

    private void consume() {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "xxx:9092,sss:9092");
        props.setProperty("group.id", "transfer.consumer_id");
        props.setProperty("auto.offset.reset", "earliest");
        props.setProperty("auto.offset.commit", "false");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("tst"));

        int limit = 20;
        for (int i = 0; i < limit; i++) {

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> r : records) {
                System.out.println(r.key());
                String json = r.value();
                System.out.println(json);
                System.out.println("offset: " + r.offset());
            }
        }
        consumer.commitSync();
        consumer.close();
    }

    public static void main(String[] args) {

    }
}
