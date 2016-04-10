package com.github.hronom.test.apache.kafka.spring.producer.controllers;

import net.moznion.random.string.RandomStringGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class ProducerController {
    private static final Logger logger = LogManager.getLogger();
    private final String stringPattern =
        "Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!";
    private final RandomStringGenerator generator = new RandomStringGenerator();
    private final String queueName = "test_topic";

    private int messageNo = 0;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    public void doSomething() {
        logger.info("Generate random string...");
        String msg = generator.generateFromPattern(stringPattern);
        logger.info("Emit to \"" + queueName + "\" message: \"" + msg + "\"");
        kafkaTemplate.send(queueName, 0, messageNo, msg);
        messageNo++;
    }
}
