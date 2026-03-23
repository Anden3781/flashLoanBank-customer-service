package com.flash_loan.bank.customer_service.repository;

import com.flash_loan.bank.customer_service.model.Customer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
