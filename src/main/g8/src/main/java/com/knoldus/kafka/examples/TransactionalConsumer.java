package com.knoldus.kafka.examples;

import com.knoldus.kafka.examples.utils.ConfigReader;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.requests.IsolationLevel;

import java.util.Collections;
import java.util.Properties;

public class TransactionalConsumer {
    
    private ConfigReader configReader = new ConfigReader();
    
    public void consume() {
        
        String kafkaServers = configReader.getKafkaServers();
        String kafkaTopic = configReader.getKafkaTopic();
        Properties consumerConfig = new Properties();
        
        consumerConfig.put("bootstrap.servers", kafkaServers);
        consumerConfig.put("group.id", "transactional-consumer-group");
        consumerConfig.put("enable.auto.commit", "false");
        consumerConfig.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerConfig.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        
        
        consumerConfig.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed"); // this has to be set
        
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerConfig);
        
        kafkaConsumer.subscribe((Collections.singletonList(kafkaTopic)));
        
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100000000);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Received Message topic =%s, partition =%s, offset = %d, key = %s, value = %s\n",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value());
            }
            
            kafkaConsumer.commitSync();
        }
    }
}
