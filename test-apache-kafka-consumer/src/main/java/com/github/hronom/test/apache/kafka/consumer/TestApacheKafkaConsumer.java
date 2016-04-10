package com.github.hronom.test.apache.kafka.consumer;

import com.github.hronom.test.apache.kafka.common.pojos.TextPojo;
import com.github.hronom.test.apache.kafka.common.utils.SerializationUtils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class TestApacheKafkaConsumer {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args)
        throws InterruptedException, IOException, ClassNotFoundException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "6000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        try (KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList("test_topic1"));
            while (true) {
                ConsumerRecords<byte[], byte[]> records = consumer.poll(1);
                for (ConsumerRecord<byte[], byte[]> record : records) {
                    String key = (String) SerializationUtils.deserialize(record.key());
                    TextPojo textPojo = (TextPojo) SerializationUtils.deserialize(record.value());
                    /*logger
                        .info("Received message: (" + key + ", " + textPojo.text + ") at offset " +
                              record.offset());*/
                }
            }
        }
    }
}
