package com.nttdata.movement.service;

import com.nttdata.movement.entity.Asociation;
import com.nttdata.movement.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AsociationService {
    Flux<Asociation> findAll();
    Mono<Asociation> findAsociation(String id);
    Mono<Customer> findCustomerById(String id);
    Mono<Product> findProductById(String id);
    Mono<Asociation> createAsociation(Asociation asociation);
    Mono<Asociation> createMovement(String idAsociation, Movement movement);
    Mono<Asociation> createTransfer(Transfer transfer);
    Mono<Asociation> createMovementMobileWallet(String cellNumber, MovementMobileWallet movementMobileWallet);
    Mono<Void> deleteAsociation(String id);
    Mono<Asociation> updateAsociation(Asociation asociation);
    Flux<List<Report1>> balanceProducts(String idCustomer);
    Mono<Report2> movementsByProduct(String idCustomer, String idProduct);

}
