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

    @PostMapping
    public Mono<Asociation> createAsociation(@Valid @RequestBody Asociation asociation){
        return asociationService.createAsociation(asociation);
    }

    @PutMapping
    public Mono<Asociation> updateAsociation(@Valid @RequestBody Asociation asociation){
        return asociationService.updateAsociation(asociation);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAsociation(@PathVariable String id){
        return asociationService.deleteAsociation(id);
    }

    @GetMapping("fCus/{id}")
    public Mono<Customer> findCustomer(@PathVariable("id") String id){
        return asociationService.findCustomerById(id);
    }

    @GetMapping("fPro/{id}")
    public Mono<Product> findProduct(@PathVariable("id") String id){
        return asociationService.findProductById(id);
    }

    @PutMapping("movements/{id}")
    public Mono<Asociation> createMovement(@PathVariable("id") String id, @RequestBody Movement movement){
        return asociationService.createMovement(id, movement);
    }

    @PutMapping("/transfers")
    public Mono<Asociation> createTransfer(@RequestBody Transfer transfer){
        return asociationService.createTransfer(transfer);
    }



    //this endpoint gets report of all products(name, number, balance) by idcustomer
    @GetMapping("/reports/{id}")
    public Flux<List<Report1>> balanceProducts(@PathVariable("id") String id){
        return asociationService.balanceProducts(id);
    }

    //this endpoint gets report all movements and transfers accord idcustomer and idproduct
    @GetMapping("/reports/{idCustomer}/{idProduct}")
    public Mono<Report2> movementsByProduct(@PathVariable("idCustomer") String idCustomer, @PathVariable("idProduct") String idProduct){
        return asociationService.movementsByProduct(idCustomer, idProduct);
    }
}
