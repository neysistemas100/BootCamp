package com.nttdata.customer.controller;

import com.nttdata.customer.entity.Customer;
import com.nttdata.customer.producer.KafkaCustomerProducer;
import com.nttdata.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    private final KafkaCustomerProducer kafkaCustomerProducer;
    @Autowired
    public CustomerController(KafkaCustomerProducer kafkaCustomerProducer) {
        this.kafkaCustomerProducer = kafkaCustomerProducer;
    }


    @GetMapping
    public Flux<Customer> findAll(){
        return customerService.findAll();

    }

    @GetMapping("/{id}")
    public Mono<Customer> findCustomer(@PathVariable String id){


        return customerService.findById(id);
    }

    @PostMapping
    public Mono<Customer> create(@Valid @RequestBody Customer customer){
        return customerService.create(customer);
    }

    @PutMapping
    public Mono<Customer> update(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return customerService.delete(id);
    }











    @PostMapping(value = "/kafka")
    public void sendMessageToKafkaTopic(@RequestBody Customer customer) {
        this.kafkaCustomerProducer.sendMessage(customer);
    }

}
