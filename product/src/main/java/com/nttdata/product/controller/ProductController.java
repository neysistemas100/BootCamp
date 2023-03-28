package com.nttdata.product.controller;

import com.nttdata.product.dto.ProductDto;
import com.nttdata.product.entity.Product;
import com.nttdata.product.service.ProductService;
import com.nttdata.product.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public Flux<Product> findAll(){
        return productService.findAll();

    }

    @GetMapping("/{id}")
    public Mono<Product> findProduct(@PathVariable String id){
        return productService.findById(id);
    }

    @PostMapping
    public Mono<Product> create(@Valid @RequestBody Product product){
        return productService.create(product);
    }

    @PutMapping
    public Mono<Product> update(@RequestBody Product product){
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return productService.delete(id);
    }
}
