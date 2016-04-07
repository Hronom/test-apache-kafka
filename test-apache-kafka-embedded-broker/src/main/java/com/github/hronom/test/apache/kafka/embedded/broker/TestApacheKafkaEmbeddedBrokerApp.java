package com.github.hronom.test.apache.kafka.embedded.broker;

import java.util.Properties;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;

/**
 * Hello world!
 */
public class TestApacheKafkaEmbeddedBrokerApp {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Properties props = new Properties();
        props.setProperty("hostname", "localhost");
        props.setProperty("port", "9090");
        props.setProperty("brokerid", "1");
        props.setProperty("log.dir", "/logs/");
        props.setProperty("enable.zookeeper", "false");
        props.setProperty("zookeeper.connect", "localhost:" + 3546 + "/kafka");

        KafkaConfig kafkaConfig = new KafkaConfig(props);

        KafkaServerStartable server = new KafkaServerStartable(kafkaConfig);
        server.startup();
    }
}
