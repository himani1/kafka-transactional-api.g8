package com.knoldus.kafka.examples.demo;

import com.knoldus.kafka.examples.TransactionalConsumer;

public class ConsumerDemo {
    
    public static void main(String[] args) {
        new TransactionalConsumer().consume();
    }
}
