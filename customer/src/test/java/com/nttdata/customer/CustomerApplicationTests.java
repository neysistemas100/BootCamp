package com.nttdata.customer;

import com.nttdata.customer.entity.Customer;
import com.nttdata.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class CustomerApplicationTests {
	@Autowired
	CustomerService customerService;

	@Test
	void contextLoads() {
	}

	@Test
	void p1(){
		Mono<Customer> aso=customerService.findByCellNumberPhone("999999999");
		aso.subscribe(a-> {
			System.out.println("Objeto essss:" +a);
		});
	}


}
