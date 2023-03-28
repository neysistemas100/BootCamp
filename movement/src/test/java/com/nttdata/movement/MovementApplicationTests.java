package com.nttdata.movement;

import com.nttdata.movement.entity.Asociation;
import com.nttdata.movement.repository.AsociationRepository;
import com.nttdata.movement.service.AsociationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class MovementApplicationTests {

	@Autowired
    AsociationService asociationService;
	@Autowired
	AsociationRepository asociationRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void p1(){
		Mono<Asociation> aso=asociationRepository.findByNumberProduct("12345678");
		aso.subscribe(
				value -> System.out.println("este valor: "+value.getId()),
				error -> error.printStackTrace(),
				() -> System.out.println("completed without a value")
		);
	}

	@Test
	void p2(){
		Flux<Asociation> aso=asociationRepository.findByIdCustomer("1");
		aso.subscribe(a-> {
			System.out.println(a.getId());
		});
	}

	@Test
	void p3(){
		asociationService.balanceProducts("1").subscribe(System.out::println);
	}

	@Test
	void p4(){
		asociationService.movementsByProduct("1","2").subscribe(System.out::println);
	}



}
