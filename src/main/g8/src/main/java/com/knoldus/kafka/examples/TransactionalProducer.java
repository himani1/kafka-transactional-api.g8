package com.knoldus.kafka.examples;

import com.knoldus.kafka.examples.utils.ConfigReader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;

import java.util.Properties;

public class TransactionalProducer {
    
    private ConfigReader configReader = new ConfigReader();
    
    public void send(int range) {
        
        String kafkaServers = configReader.getKafkaServers();
        String kafkaTopic = configReader.getKafkaTopic();
        Properties producerConfig = new Properties();
        
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "transactional-producer");
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        
        
        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // enable idempotence
        producerConfig.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "test-transactional-id"); // set transaction id
        producerConfig.put(ProducerConfig.RETRIES_CONFIG, 3);
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerConfig.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        
        
        Producer<String, String> producer = new KafkaProducer<>(producerConfig);
        
        try {
            producer.initTransactions(); //initiate transactions
            producer.beginTransaction(); //begin transactions
            for (int value = 1; value < range; value++) {
                producer.send(new ProducerRecord<String, String>(kafkaTopic, Integer.toString(value)));
            }
            producer.commitTransaction(); //commit
            
            
        } catch (ProducerFencedException e) {
            producer.close();
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();
    }
    
}