package com.nttdata.movement.controller;

import com.nttdata.movement.entity.Asociation;
import com.nttdata.movement.model.*;
import com.nttdata.movement.service.AsociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/asociations")
public class AsociationController {

    @Autowired
    AsociationService asociationService;

    @GetMapping
    public Flux<Asociation> findAll(){
        return asociationService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Asociation> findAsociation(@PathVariable("id") String id){
        return asociationService.findAsociation(id);
    }

    @GetMapping("fCus/{id}")
    public Mono<Customer> findCustomer(@PathVariable("id") String id){
        return asociationService.findCustomerById(id);
    }

    @GetMapping("fPro/{id}")
    public Mono<Product> findProduct(@PathVariable("id") String id){
        return asociationService.findProductById(id);
    }

    @PostMapping
    public Mono<Asociation> create(@Valid @RequestBody Asociation asociation){
        return asociationService.create(asociation);
    }

    @PutMapping("movements/{id}")
    public Mono<Asociation> createMovement(@PathVariable("id") String id, @RequestBody Movement movement){
        return asociationService.createMovement(id, movement);
    }

    @PutMapping("/transfers")
    public Mono<Asociation> createTransfer(@RequestBody Transfer transfer){
        return asociationService.createTransfer(transfer);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return asociationService.delete(id);
    }

    @GetMapping("/reports/{id}")
    Flux<List<Report1>> balanceProducts(@PathVariable("id") String id){
        return asociationService.balanceProducts(id);
    }

    @GetMapping("/reports/{idCustomer}/{idProduct}")
    public Mono<Report2> movementsByProduct(@PathVariable("idCustomer") String idCustomer, @PathVariable("idProduct") String idProduct){
        return asociationService.movementsByProduct(idCustomer, idProduct);
    }
}
