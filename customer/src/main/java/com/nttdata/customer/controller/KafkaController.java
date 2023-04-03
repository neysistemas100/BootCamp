package com.nttdata.customer.controller;

import com.nttdata.customer.entity.Customer;
import com.nttdata.customer.producer.KafkaCustomerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {
    private final KafkaCustomerProducer kafkaCustomerProducer;

    @Autowired
    KafkaController(KafkaCustomerProducer kafkaCustomerProducer) {
        this.kafkaCustomerProducer = kafkaCustomerProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestBody Customer customer) {
        this.kafkaCustomerProducer.sendMessage(customer);
    }
}
