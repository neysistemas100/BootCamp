package com.nttdata.product.service;


import com.nttdata.product.dto.ProductDto;
import com.nttdata.product.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> findAll();
    Mono<Product> findById(String id);
    Mono<Product> create(Product product);
    Mono<Product> update(Product product);
    Mono<Void> delete(String id);

}
