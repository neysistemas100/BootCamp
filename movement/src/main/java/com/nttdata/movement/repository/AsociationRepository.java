package com.nttdata.movement.repository;

import com.nttdata.movement.entity.Asociation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AsociationRepository extends ReactiveMongoRepository<Asociation,String > {
    Mono<Asociation> findByNumberAccount(String numberAccount);
    Flux<Asociation> findByIdCustomer(String IdCustomer);
    Mono<Asociation> findByIdCustomerAndIdProduct(String IdCustomer, String IdProduct);
    Mono<Asociation> findByCellNumberPhone(String CellNumberPhone);

}
