package com.nttdata.movement.service.imp;

import com.nttdata.movement.entity.Asociation;
import com.nttdata.movement.feignclient.CustomerFeignClient;
import com.nttdata.movement.model.*;
import com.nttdata.movement.repository.AsociationRepository;
import com.nttdata.movement.service.AsociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsociationServiceImp implements AsociationService {

    CustomerFeignClient customerFeignClient;
    @Autowired
    private AsociationRepository asociationRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Flux<Asociation> findAll() {
        /*return webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/customers")
                .retrieve()
                .bodyToFlux(Customer.class);*/
        return asociationRepository.findAll();
    }

    @Override
    public Mono<Asociation> findAsociation(String id) {
        return asociationRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Asociation's ID does not exist")));
    }

    @Override
    public Mono<Customer> findCustomerById(String id) {
        return webClientBuilder.build()
                .get()
                .uri("http://customer-service/customers/{id}", id)//http://localhost:8081/customers/{id}
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(Exception.class)
                                .flatMap(e -> Mono.error(new RuntimeException("Customer'ID does not exist"))))
                .bodyToMono(Customer.class);


                /*.onStatus(
                        HttpStatus::isError,
                        response -> response.bodyToMono(Exception.class).map(e->new RuntimeException("Customer's ID does not exist")))
                .bodyToMono(Customer.class);*/

        //.bodyToMono(Customer.class)
        //.switchIfEmpty(Mono.error(new RuntimeException("Customer's ID does not exist")));

    }

    @Override
    public Mono<Product> findProductById(String id) {
        return webClientBuilder.build()
                .get()
                .uri("http://product-service/products/{id}", id)//http://localhost:8082/products/{id}
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(Exception.class)
                                .flatMap(e -> Mono.error(new RuntimeException("Product'ID does not exist"))))
                .bodyToMono(Product.class);
    }

    @Override
    public Mono<Asociation> createAsociation(Asociation asociation) {
        /*return this.findCustomerById(asociation.getIdCustomer())
                .then(this.findProductById(asociation.getIdProduct()))
                .flatMap(m -> asociationRepository.insert(asociation));*/

        return this.findCustomerById(asociation.getIdCustomer())
                .then(this.findProductById(asociation.getIdProduct()))
                .flatMap(m -> {
                    asociation.setMovements(new ArrayList<>());
                    asociation.setTransfers(new ArrayList<>());
                    asociation.setMovementMobileWallets(new ArrayList<>());
                    return asociationRepository.insert(asociation);
                });
    }

    @Override
    public Mono<Asociation> createMovement(String idAsociation, Movement movement) {
        return asociationRepository.findById(idAsociation)
                .switchIfEmpty(Mono.error(new RuntimeException("Asociation's ID does not exist")))
                .flatMap(m -> {
                    if (movement.getType().equalsIgnoreCase("D")) {

                        //movement.setDate(LocalDateTime.now());
                        //System.out.println("fecha: "+movement.getDate());
                        m.getMovements().add(movement);
                        //System.out.println("barza");
                        m.setBalance(m.getBalance() + movement.getAmount());
                    } else if (movement.getType().equalsIgnoreCase("R")) {
                        //movement.setDate(LocalDateTime.now());
                        m.getMovements().add(movement);
                        m.setBalance(m.getBalance() - movement.getAmount());
                        if (m.getBalance() < 0)
                            return Mono.error(new RuntimeException("You don´t have enough money for the operation"));
                    }
                    return asociationRepository.save(m);
                });


    }


    @Override
    public Mono<Asociation> createTransfer(Transfer transfer) {
        /*return asociationRepository.findByNumberProduct(transfer.getSourceAccount())
                .switchIfEmpty(Mono.error(new RuntimeException("Source account does not exist")))
                .then(asociationRepository.findByNumberProduct(transfer.getDestinationAccount()))
                .switchIfEmpty(Mono.error(new RuntimeException("Destination account does not exist")))*/

        Mono<Asociation> a1 = asociationRepository.findByNumberProduct(transfer.getSourceAccount())
                .switchIfEmpty(Mono.error(new RuntimeException("Source account does not exist")));
        Mono<Asociation> a2 = asociationRepository.findByNumberProduct(transfer.getDestinationAccount())
                .switchIfEmpty(Mono.error(new RuntimeException("Destination account does not exist")));

        return Mono.zip(a1, a2)
                .flatMap(tuple -> {
                    if (tuple.getT1().getBalance() < transfer.getAmount())
                        return Mono.error(new RuntimeException("You don´t have enough money to make the transfer"));
                    tuple.getT1().getTransfers().add(transfer);
                    tuple.getT1().setBalance(tuple.getT1().getBalance() - transfer.getAmount());
                    tuple.getT2().setBalance(tuple.getT2().getBalance() + transfer.getAmount());
                    return asociationRepository.save(tuple.getT2())
                            .then(asociationRepository.save(tuple.getT1()));
                });

    }



    @Override
    public Mono<Asociation> createMovementMobileWallet(String cellNumber, MovementMobileWallet movementMobileWallet) {
        return asociationRepository.findByCellNumberPhone(cellNumber)
                .switchIfEmpty(Mono.error(new RuntimeException("Asociation with that cell number doesn´t exist")))
                .flatMap(m -> {
                    if (movementMobileWallet.getType().equalsIgnoreCase("R")) {
                        m.getMovementMobileWallets().add(movementMobileWallet);
                        m.setBalance(m.getBalance() + movementMobileWallet.getAmount());
                    } else if (movementMobileWallet.getType().equalsIgnoreCase("E")) {
                        m.getMovementMobileWallets().add(movementMobileWallet);
                        m.setBalance(m.getBalance() - movementMobileWallet.getAmount());
                        if (m.getBalance() < 0)
                            return Mono.error(new RuntimeException("You don´t have enough money for the operation"));
                    }
                    return asociationRepository.save(m);
                });

    }


    @Override
    public Mono<Void> deleteAsociation(String id) {
        return asociationRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Asociation's ID does not exist")))
                .flatMap(m -> asociationRepository.deleteById(m.getId()));
    }

    @Override
    public Mono<Asociation> updateAsociation(Asociation asociation) {
        return asociationRepository.findById(asociation.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Asociation's ID does not exist")))
                .flatMap(a -> {
                    a.setNumberProduct(asociation.getNumberProduct());
                    a.setBalance(asociation.getBalance());
                    return asociationRepository.save(a);
                });
    }

    @Override
    public Flux<List<Report1>> balanceProducts(String idCustomer) {
        List<Report1> list = new ArrayList<>();
        /*return asociationRepository.findByIdCustomer(idCliente)
                .flatMap(a->{
                    System.out.println("id son:"+a.getId());
                    map.put("idProduct",Mono.just(a.getIdProduct()));
                    System.out.println("id product: "+Mono.just(a.getIdProduct()));
                    map.put("name",findProductById(a.getIdProduct()).map(Product::getName));
                    map.put("numerAccount",Mono.just(a.getNumberProduct()));
                    map.put("Balance",Mono.just(a.getBalance().toString()));
                    return Flux.just(map);
                });*/

       /* Flux<Asociation> a = asociationRepository.findByIdCustomer(idCliente);

        return a.flatMap(b->{
            Map<String,String> map=new HashMap<>();
            findProductById(b.getIdProduct())
                    .flatMap(c->{
                       map.put("idProduct",b.getIdProduct());
                       map.put("name",c.getName());
                       map.put("Balance",b.getBalance().toString());
                        return Mono.just(map);
                    });
            return Flux.just(map);
        });*/

        return asociationRepository.findByIdCustomer(idCustomer)
                .flatMap(a -> {
                    System.out.println("id son:" + a.getId());
                    Report1 report1 = new Report1();
                    report1.setNameProduct(this.findProductById(a.getIdProduct()).map(Product::getName).block());
                    report1.setNumberAccount(a.getNumberProduct());
                    report1.setBalance(a.getBalance());
                    list.add(report1);
                    System.out.println("idProducts son:" + a.getIdProduct());
                    System.out.println("balance son:" + a.getBalance());
                    System.out.println("lista: " + list);
                    return Flux.just(list);
                });

    }

    @Override
    public Mono<Report2> movementsByProduct(String idCustomer, String idProduct) {
        return asociationRepository.findByIdCustomer(idCustomer).filter(a -> a.getIdProduct().equals(idProduct))
                .next()
                .flatMap(a -> {
                    Report2 report2 = new Report2();
                    report2.setNameProduct(this.findProductById(a.getIdProduct()).map(Product::getName).block());
                    report2.setNumberAccount(a.getNumberProduct());
                    report2.setBalance(a.getBalance());
                    report2.setMovements(a.getMovements());
                    report2.setTransfers(a.getTransfers());
                    return Mono.just(report2);
                });
    }


}
