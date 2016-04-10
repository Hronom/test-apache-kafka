package com.github.hronom.test.apache.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TestApacheKafkaProducerApp {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);

        final boolean sync = false;

        int messageNo = 1;
        while (true) {
            String topic = "test_topic";
            Integer key = messageNo;
            String value = "test_value";
            ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(topic, key, value);
            if (sync) {
                producer.send(producerRecord).get();
            } else {
                producer.send(producerRecord);
            }
            System.out.println("Sent message: (" + key + ", " + value + ")");
            messageNo++;
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        }
        //producer.close();
    }
}
