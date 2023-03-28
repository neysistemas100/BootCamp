package com.nttdata.product.service.imp;


import com.nttdata.product.dto.ProductDto;
import com.nttdata.product.entity.Product;
import com.nttdata.product.repository.ProductRepository;
import com.nttdata.product.service.ProductService;

import com.nttdata.product.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(String id) {
       return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product's ID does not exist")));
    }

    @Override
    public Mono<Product> create(Product product) {
        return productRepository.findById(product.getId())
                .flatMap(p->Mono.<Product>error(new RuntimeException("Product already exists")))
                .switchIfEmpty(productRepository.insert(product));
    }

    @Override
    public Mono<Product> update(Product product) {
        return productRepository.findById(product.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Product's ID does not exist")))
                .flatMap(p->productRepository.save(product));
    }

    @Override
    public Mono<Void> delete(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product's ID does not exist")))
                .flatMap(p->productRepository.deleteById(p.getId()));
    }
}
