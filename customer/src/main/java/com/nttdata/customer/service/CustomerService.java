package com.nttdata.customer.service;

import com.nttdata.customer.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<Customer> findAll();
    Mono<Customer> findById(String id);
    Mono<Customer> create(Customer customer);
    Mono<Customer> update(Customer customer);
    Mono<Void> delete(String id);
}
