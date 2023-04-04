package com.nttdata.customer.repositoryRedis;

import com.nttdata.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerDao {
    private final ReactiveRedisOperations<String, Customer> reactiveRedisOperations;

    public Flux<Customer> findAll(){
        return this.reactiveRedisOperations.opsForList().range("customers", 0, -1);
    }

    public Mono<Customer> findById(String id) {
        return this.findAll().filter(p -> p.getId().equals(id)).last();
    }


    public Mono<Long> save(Customer customer){
        return this.reactiveRedisOperations. opsForList().rightPush("customers", customer);
    }

    public Mono<Boolean> deleteAll() {
        return this.reactiveRedisOperations.opsForList().delete("posts");
    }
}
