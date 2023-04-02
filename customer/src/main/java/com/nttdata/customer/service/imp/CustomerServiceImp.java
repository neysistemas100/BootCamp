package com.nttdata.customer.service.imp;

import com.nttdata.customer.entity.Customer;
import com.nttdata.customer.repository.CustomerRepository;
import com.nttdata.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.status;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findById(String id) {
        /*return Mono.just(Customer.class)
                .flatMap(c->customerRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Customer's ID does not exist")));*/

        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Customer's ID does not exist")));
    }

    @Override
    public Mono<Customer> create(Customer customer) {
        return customerRepository.findById(customer.getId())
                .flatMap(c->Mono.<Customer>error(new RuntimeException("Customer already exists")))
                .switchIfEmpty(customerRepository.insert(customer));

    }

    @Override
    public Mono<Customer> update(Customer customer) {
        /*return Mono.just(Customer.class)
                .flatMap(c->this.findById(customer.getId()))
                .flatMap(c->customerRepository.save(c));*/
        return customerRepository.findById(customer.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Customer's ID does not exist")))
                .flatMap(c->customerRepository.save(customer));
    }

    @Override
    public Mono<Void> delete(String id) {
        /*return Mono.just(Customer.class)
                .flatMap(c->this.findById(id))
                .flatMap(c->customerRepository.deleteById(c.getId()));*/

        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Customer's ID does not exist")))
                .flatMap(c->customerRepository.deleteById(c.getId()));


    }

    public Mono<Customer> findByCellNumberPhone(String cellNumber){
        return customerRepository.findByCellNumberPhone(cellNumber);
    }
}
