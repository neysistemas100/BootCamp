package com.nttdata.movement;

import com.nttdata.movement.entity.Asociation;
import com.nttdata.movement.model.MovementMobileWallet;
import com.nttdata.movement.repository.AsociationRepository;
import com.nttdata.movement.service.AsociationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
		Mono<Asociation> aso=asociationRepository.findByNumberAccount("12345678");
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
		asociationRepository.findAll()
				.filter(a->a.getIdCustomer().equals("6"))
				.filter(a->a.getIdProduct().equals("10"))
				.next()
				.subscribe(b-> System.out.println("MADRIDDDDDDDDD: "+b));
	}

	@Test
	void p5(){
		asociationService.createMovementMobileWallet("999999999",new MovementMobileWallet("R",10.0, LocalDateTime.now()))
				.subscribe(b-> System.out.println("MADRIDDDDDDDDD: "+b));
		//asociationRepository.findByCellNumberPhone("999999999")
		//		.subscribe(a-> System.out.println("barza: "+a));
	}

	@Test
	void p6(){
		asociationService.updateAccountAsociated("6429aff5321884582abbeed3","123321")
				.subscribe(l-> System.out.println("logrado "+l));
	}




}
