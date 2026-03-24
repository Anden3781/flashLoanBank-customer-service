package com.flash_loan.bank.customer_service.controller;

import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.service.CustomerService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Crear un nuevo cliente (Individual o Business)
    @PostMapping
    public Single<ResponseEntity<CustomerDto>> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto)
                .map(createdDto -> new ResponseEntity<>(createdDto, HttpStatus.CREATED));
    }

    // Listar todos los clientes (Flujo reactivo)
    @GetMapping
    public Observable<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Single<ResponseEntity<CustomerDto>> getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Single.just(ResponseEntity.notFound().build()));
    }
}
