package com.nttdata.movement.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.nttdata.movement.entity.Asociation;
import com.nttdata.movement.model.Customer;
import com.nttdata.movement.repository.AsociationRepository;
import com.nttdata.movement.service.AsociationService;
import lombok.Builder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.bouncycastle.jcajce.provider.symmetric.ChaCha;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AsociationServiceImpTest {

    @InjectMocks
    private AsociationServiceImp asociationServiceImp;
    @MockBean
    private AsociationService asociationService;
    @Mock
    private AsociationRepository asociationRepository;
    @Mock
    private WebClient.Builder webClientBuilder;

    private Asociation asociation;
    String asociationId = "123";

    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());

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
    void findCustomerById() throws JsonProcessingException {
        new MockServerClient("127.0.0.1", 1080)
                .when(
                        request()
                                .withMethod("get")
                                .withPath("/customers")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact("{id: '10'}")),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(401)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("{ message: 'incorrect username and password combination' }")
                                .withDelay(TimeUnit.SECONDS,1)
                );



        ObjectMapper objectMapper = new ObjectMapper();
        Customer mockCustomer = new Customer();
        mockCustomer.setId("10");
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockCustomer))
                .addHeader("Content-Type", "application/json"));

        Mono<Customer> customerMono = asociationServiceImp.findCustomerById("10");

        StepVerifier.create(customerMono)
                .assertNext(c->{
                    assertEquals(c.getId(), "10");

                })
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