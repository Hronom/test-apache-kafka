package com.github.hronom.test.apache.kafka.producer;

import com.github.hronom.test.apache.kafka.common.pojos.TextPojo;
import com.github.hronom.test.apache.kafka.common.utils.SerializationUtils;

import net.moznion.random.string.RandomStringGenerator;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TestApacheKafkaProducer {
    private static final Logger logger = LogManager.getLogger();

    private static final String topic = "test_topic";
    private static final String stringPattern
        = "Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!Ccn!CCccCn!cccccccCn!!Ccccc!cc!ccccc!cccc!!Cc!C!C!";
    private static final RandomStringGenerator generator = new RandomStringGenerator();


    public static void main(String[] args)
        throws ExecutionException, InterruptedException, IOException, ClassNotFoundException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");

        try (KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(props)) {
            int totalCountOfSendedMessages = 0;
            long totalSendTime = 0;

            long timeOfLastUpdate = 0;
            int countOfMessagesInSec = 0;

            while (true) {
                TextPojo textPojo = new TextPojo();
                textPojo.text = generator.generateFromPattern(stringPattern);

                byte[] dataKey = SerializationUtils.serialize("input_text");
                byte[] dataValue = SerializationUtils.serialize(textPojo);

                ProducerRecord<byte[], byte[]> producerRecord = new ProducerRecord<>(
                    topic,
                    dataKey,
                    dataValue
                );

                long sendingStartTime = System.currentTimeMillis();
                // Sync send
                producer.send(producerRecord).get();
                long currentTime = System.currentTimeMillis();

                long sendTime = currentTime - sendingStartTime;

                totalSendTime += sendTime;

                totalCountOfSendedMessages++;
                countOfMessagesInSec++;
                if (currentTime - timeOfLastUpdate > TimeUnit.SECONDS.toMillis(1)) {
                    logger.info("Average send time: " +
                                (double) (totalSendTime / totalCountOfSendedMessages) + " ms.");
                    logger.info("Count of messages in second: " + countOfMessagesInSec);

                    timeOfLastUpdate = currentTime;
                    countOfMessagesInSec = 0;
                }
            }
        }
    }
}
