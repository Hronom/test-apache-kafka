package com.github.hronom.test.apache.kafka.spring.consumer.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
public class ConsumerController {
    private static final Logger logger = LogManager.getLogger();
    private final String queueName = "test_topic";

    @KafkaListener(id = "foo", topics = queueName)
    public void processQueue(String message) {
        logger.info("Received from \"" + queueName + "\" message: \"" + message + "\"");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}