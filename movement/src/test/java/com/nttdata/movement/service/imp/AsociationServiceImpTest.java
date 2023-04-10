package com.nttdata.movement.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.nttdata.movement.entity.Asociation;
import com.nttdata.movement.model.Customer;
import com.nttdata.movement.repository.AsociationRepository;
import com.nttdata.movement.service.AsociationService;
import lombok.Builder;
;
import org.assertj.core.api.Assertions;
import org.bouncycastle.jcajce.provider.symmetric.ChaCha;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.testng.annotations.BeforeClass;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.Cookie.cookie;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;
import static reactor.core.publisher.Mono.when;


@ExtendWith(MockitoExtension.class)

@SpringBootTest
class AsociationServiceImpTest {

    @InjectMocks
    private AsociationServiceImp asociationServiceImp;
    @Mock
    private AsociationRepository asociationRepository;
    private Asociation asociation;
    private static ClientAndServer mockServer;
    String asociationId = "123";

    //public static MockWebServer mockBackEnd;

    /*@BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }
    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }*/

    @BeforeAll
    public static void startServer() {
        mockServer = startClientAndServer(1081);
    }

    @AfterAll
    public static void stopServer() {
        mockServer.stop();
    }

    @Test
    void findAsociation() {
        asociation = Asociation.builder()
                .id("123")
                .idCustomer("1")
                .idProduct("1")
                .numberAccount("999888")
                .balance(100.0)
                .build();

        Mockito.when(asociationRepository.findById(asociationId)).thenReturn(Mono.just(asociation));
        //Mono<Asociation> asociationMono = asociationService.findAsociation(asociationId);
        StepVerifier
                .create(asociationServiceImp.findAsociation(asociationId))
                .assertNext(a->{
                    assertEquals(a.getId(), "123");
                    assertEquals(a.getIdCustomer(), "1");
                    assertEquals(a.getIdProduct(), "1");
                    assertEquals(a.getNumberAccount(), "999888");
                })
                //.consumeNextWith(a->{
                  //  assertThat(a.getId().equals("123"));

               // })
                .verifyComplete();


    }

    @Test
    void findCustomerById() {
        Customer customer = new Customer();
        customer.setId("1");
        mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/customer-service/customers/1")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("{'id': '1'}")
                );


        StepVerifier
                .create(asociationServiceImp.findCustomerById("1"))
                .assertNext(c->{
                    assertEquals(c.getId(), "1");

                })
                //.consumeNextWith(a->{
                //  assertThat(a.getId().equals("123"));

                // })
                .verifyComplete();

    }



    @Test
    void findProductById() {
    }

    @Test
    void createAsociation() {

    }

    @Test
    void createMovement() {
    }

    @Test
    void createTransfer() {
    }
}