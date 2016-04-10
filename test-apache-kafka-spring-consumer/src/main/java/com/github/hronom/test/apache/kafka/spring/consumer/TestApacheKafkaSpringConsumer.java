package com.github.hronom.test.apache.kafka.spring.consumer;

import com.github.hronom.test.apache.kafka.spring.consumer.configs.ApacheKafkaConsumerConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@ComponentScan
@Import(ApacheKafkaConsumerConfiguration.class)
public class TestApacheKafkaSpringConsumer {
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(TestApacheKafkaSpringConsumer.class).run(args);
    }
}