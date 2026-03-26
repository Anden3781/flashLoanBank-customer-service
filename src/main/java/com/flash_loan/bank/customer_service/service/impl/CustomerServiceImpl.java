package com.flash_loan.bank.customer_service.service.impl;

import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.exception.CustomerAlreadyExistsException;
import com.flash_loan.bank.customer_service.mapper.CustomerMapper;
import com.flash_loan.bank.customer_service.model.Customer;
import com.flash_loan.bank.customer_service.repository.CustomerRepositoryPort;
import com.flash_loan.bank.customer_service.service.CustomerService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepositoryPort repository;
    private final CustomerMapper mapper;

    @Override
    public Single<CustomerDto> createCustomer(CustomerDto customerDto) {
        return repository.existsByEmail(customerDto.getEmail())
                .filter(exists -> !exists)
                .switchIfEmpty(Maybe.error(new CustomerAlreadyExistsException("Email already registered: " + customerDto.getEmail())))
                .map(unused -> mapper.toEntity(customerDto))
                .flatMapSingle(repository::save)
                .map(mapper::toDto)
                .toSingle();
    }

    @Override
    public Maybe<CustomerDto> getCustomerById(String id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public Flowable<CustomerDto> getAllCustomers() {
        return repository.findAll()
                .map(mapper::toDto);
    }

    @Override
    public Single<CustomerDto> updateCustomer(String id, CustomerDto customerDto) {
        return repository.findById(id)
                .switchIfEmpty(Maybe.error(new RuntimeException("Customer not found: " + id)))
                .map(existing -> {
                    Customer updated = mapper.toEntity(customerDto);
                    updated.setId(id); // Ensure ID is preserved
                    return updated;
                })
                .flatMapSingle(repository::save)
                .map(mapper::toDto)
                .toSingle();
    }

    @Override
    public Completable deleteCustomer(String id) {
        return repository.deleteById(id)
                .filter(success -> success)
                .switchIfEmpty(Maybe.error(new RuntimeException("Customer not found for deletion: " + id)))
                .ignoreElement();
    }
}