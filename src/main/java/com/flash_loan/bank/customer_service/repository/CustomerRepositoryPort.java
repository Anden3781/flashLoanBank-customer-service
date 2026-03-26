package com.flash_loan.bank.customer_service.repository;

import com.flash_loan.bank.customer_service.model.Customer;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface CustomerRepositoryPort {
    Single<Customer> save(Customer customer);
    Maybe<Customer> findById(String id);
    Flowable<Customer> findAll();
    Single<Boolean> existsByEmail(String email);
    Single<Boolean> deleteById(String id);
}
