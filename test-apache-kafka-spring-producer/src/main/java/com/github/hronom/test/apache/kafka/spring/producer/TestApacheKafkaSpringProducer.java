package com.github.hronom.test.apache.kafka.spring.producer;

import com.github.hronom.test.apache.kafka.spring.producer.configs.ApacheKafkaProducerConfiguration;
import com.github.hronom.test.apache.kafka.spring.producer.configs.AppConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@ComponentScan
@Import(value = {AppConfig.class, ApacheKafkaProducerConfiguration.class})
public class TestApacheKafkaSpringProducer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestApacheKafkaSpringProducer.class, args);
    }
}