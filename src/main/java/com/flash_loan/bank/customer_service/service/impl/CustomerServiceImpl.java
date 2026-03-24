package com.flash_loan.bank.customer_service.service.impl;

import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.mapper.CustomerMapper;
import com.flash_loan.bank.customer_service.repository.CustomerRepository;
import com.flash_loan.bank.customer_service.service.CustomerService;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public Single<CustomerDto> createCustomer(CustomerDto customerDto) {
        return Single.fromPublisher(repository.existsByEmail(customerDto.getEmail()))
                .filter(exists -> !exists)
                .switchIfEmpty(Single.error(new RuntimeException("El email ya está registrado"))) // Aquí vuelve a ser Single<Boolean>
                .flatMap(unused ->
                        Single.fromPublisher(repository.save(mapper.toEntity(customerDto)))
                )
                .map(mapper::toDto);
    }

    @Override
    public Maybe<CustomerDto> getCustomerById(String id) {
        return Maybe.fromPublisher(repository.findById(id))
                .map(mapper::toDto);
    }

    @Override
    public Observable<CustomerDto> getAllCustomers() {
        return Observable.fromPublisher(repository.findAll())
                .map(mapper::toDto);
    }
}