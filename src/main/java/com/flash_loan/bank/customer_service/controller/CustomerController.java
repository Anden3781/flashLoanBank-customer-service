package com.flash_loan.bank.customer_service.controller;

import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.service.CustomerService;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Single<ResponseEntity<CustomerDto>> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto)
                .map(createdDto -> ResponseEntity.status(HttpStatus.CREATED).body(createdDto));
    }

    @GetMapping
    public Flowable<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Single<ResponseEntity<CustomerDto>> getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Single<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(id, customerDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Single<ResponseEntity<Void>> deleteCustomer(@PathVariable String id) {
        return customerService.deleteCustomer(id)
                .toSingleDefault(ResponseEntity.noContent().<Void>build())
                .onErrorReturn(error -> ResponseEntity.notFound().build());
    }
}
