package com.knoldus.kafka.examples.demo;

import com.knoldus.kafka.examples.TransactionalProducer;

public class ProducerDemo {
    
    public static void main(String[] args) {
        new TransactionalProducer().send(10);
    }
}
