package com.nttdata.movement.feignclient;

import com.nttdata.movement.model.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "customer-service", url = "http://localhost:8082/customers")
//@RequestMapping("/customers")
public interface CustomerFeignClient {
    @GetMapping
    Flux<Customer> findAll();

    @GetMapping("/{id}")
    Mono<Customer> findCustomer(@PathVariable String id);
}
