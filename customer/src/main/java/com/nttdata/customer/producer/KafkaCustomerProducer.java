package com.nttdata.customer.producer;

import com.nttdata.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaCustomerProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaCustomerProducer.class);
    private final KafkaTemplate<String, Customer> kafkaTemplate;

    @Autowired
    public KafkaCustomerProducer(KafkaTemplate<String, Customer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Customer customer) {
        LOGGER.info("Producing message {}", customer);
        this.kafkaTemplate.send("TOPIC-CUSTOMER", customer);
    }
}
