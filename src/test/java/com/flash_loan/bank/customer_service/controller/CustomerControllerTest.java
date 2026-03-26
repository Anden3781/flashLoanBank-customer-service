package com.flash_loan.bank.customer_service.controller;

import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.dto.PersonalCustomerDto;
import com.flash_loan.bank.customer_service.service.CustomerService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CustomerController.class)
@ActiveProfiles("test")
class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CustomerService customerService;

    private PersonalCustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerDto = new PersonalCustomerDto();
        customerDto.setId("1");
        customerDto.setEmail("test@test.com");
        customerDto.setFirstName("John");
        customerDto.setLastName("Doe");
        customerDto.setDocumentNumber("12345678");
        customerDto.setPhone("987654321");
        customerDto.setCustomerType("PERSONAL");
    }

    @Test
    void createCustomer_ShouldReturnCreated() {
        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(Single.just(customerDto));

        webTestClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.email").isEqualTo("test@test.com");
    }

    @Test
    void getAllCustomers_ShouldReturnList() {
        when(customerService.getAllCustomers()).thenReturn(Flowable.just(customerDto));

        webTestClient.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonalCustomerDto.class)
                .hasSize(1);
    }

    @Test
    void getCustomerById_WhenExists_ShouldReturnCustomer() {
        when(customerService.getCustomerById(anyString())).thenReturn(Maybe.just(customerDto));

        webTestClient.get()
                .uri("/api/v1/customers/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    void getCustomerById_WhenNotExists_ShouldReturnNotFound() {
        when(customerService.getCustomerById(anyString())).thenReturn(Maybe.empty());

        webTestClient.get()
                .uri("/api/v1/customers/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateCustomer_ShouldReturnUpdated() {
        when(customerService.updateCustomer(anyString(), any(CustomerDto.class))).thenReturn(Single.just(customerDto));

        webTestClient.put()
                .uri("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    void deleteCustomer_WhenSuccess_ShouldReturnNoContent() {
        when(customerService.deleteCustomer(anyString())).thenReturn(Completable.complete());

        webTestClient.delete()
                .uri("/api/v1/customers/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteCustomer_WhenError_ShouldReturnNotFound() {
        when(customerService.deleteCustomer(anyString())).thenReturn(Completable.error(new RuntimeException("Not found")));

        webTestClient.delete()
                .uri("/api/v1/customers/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createCustomer_InvalidPayload_ShouldReturnBadRequest() {
        PersonalCustomerDto invalid = new PersonalCustomerDto();
        invalid.setCustomerType("PERSONAL");
        invalid.setEmail("bad-email");
        invalid.setPhone("123");

        webTestClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalid)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
