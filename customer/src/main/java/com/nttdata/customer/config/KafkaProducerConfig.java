package com.nttdata.customer.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.nttdata.customer.entity.Customer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final String bootstrapAddress = "localhost:9092";

    @Bean
    public ProducerFactory<String, Customer> producerFactory(){
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new  DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "kafkaStringTemplate")
    public KafkaTemplate<String, Customer> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
