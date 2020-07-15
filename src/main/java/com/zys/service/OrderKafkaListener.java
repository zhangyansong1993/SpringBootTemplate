package com.zys.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
public class OrderKafkaListener {
    private final static Logger logger = LoggerFactory.getLogger(OrderKafkaListener.class);

    @KafkaListener(topics = {"${topic}"})
    public void receive(ConsumerRecord<String, String> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
         if (kafkaMessage.isPresent()) {
            logger.info("Receive info [offset = "+record.offset()+", key = "+record.key()+", value = "+record.value()+"]");

         }

    }
}
