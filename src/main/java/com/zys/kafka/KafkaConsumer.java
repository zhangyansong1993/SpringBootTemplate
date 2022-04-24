package com.zys.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    /**
     * listener一次性消费500条数据（配置），方法单条处理
     * ack-mode: manual_immediate  处理一条提交一次
     * @param record
     * @param ack
     */
    @KafkaListener(topics = "test", groupId = "testGroup")
    private void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        //消费到的数据放到队列中，由下游去处理
        System.out.println("headers: "+record.headers());
        System.out.println("key: "+record.key());
        System.out.println("value: "+record.value());
        System.out.println("topic: "+record.topic());
        System.out.println("partition: "+record.partition());
        System.out.println("offset: "+record.offset());
        System.out.println("record: "+record);
        //手动提交ack，配置中自动提交ack设为false否则不生效
        ack.acknowledge();
    }

    /**
     * 消费多个主题、指定分区消费、指定偏移量消费、设置消费者数量
     * @param record
     * @param ack
     */
    @KafkaListener(groupId = "testGroup",topicPartitions = {
            @TopicPartition(topic = "test1",partitions = {"0","1"}),//消费多个主题，可以指定分区消费
            @TopicPartition(topic = "test2",partitions = "0",
                    partitionOffsets = @PartitionOffset(partition = "1",initialOffset = "100"))//指定偏移量消费
    },concurrency = "3")//启动3个消费者，不超过分区数
    private void listenPro(ConsumerRecord<String, String> record, Acknowledgment ack) {
        //消费到的数据放到队列中，由下游去处理
        System.out.println("headers: "+record.headers());
        System.out.println("key: "+record.key());
        System.out.println("value: "+record.value());
        System.out.println("topic: "+record.topic());
        System.out.println("partition: "+record.partition());
        System.out.println("offset: "+record.offset());
        System.out.println("record: "+record);
        //手动提交ack，配置中自动提交ack设为false否则不生效
        ack.acknowledge();
    }
    /**
     * listener一次性消费500条数据（配置），方法批量处理
     * ack-mode: manual  处理完500条后提交
     * @param records
     * @param ack
     */
//    @KafkaListener(topics = "test1", groupId = "testGroup1")
    private void listens(ConsumerRecords<String, String> records, Acknowledgment ack) {

    }
}
