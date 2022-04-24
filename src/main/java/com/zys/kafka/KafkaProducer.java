package com.zys.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final static String KAFKA_TOPIC = "test";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send() {
        kafkaTemplate.send(KAFKA_TOPIC,"thisIsKey","this is test data");
    }
}
