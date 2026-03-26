package com.flash_loan.bank.customer_service.infrastructure.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveCustomerMongoRepository extends ReactiveMongoRepository<CustomerEntity, String> {
    Mono<Boolean> existsByEmail(String email);
}
