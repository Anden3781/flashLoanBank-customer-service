package com.flash_loan.bank.customer_service.infrastructure.persistence;

import com.flash_loan.bank.customer_service.model.Customer;
import com.flash_loan.bank.customer_service.repository.CustomerRepositoryPort;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final ReactiveCustomerMongoRepository repository;
    private final CustomerPersistenceMapper mapper;

    @Override
    public Single<Customer> save(Customer customer) {
        return Single.fromPublisher(repository.save(mapper.toEntity(customer)))
                .map(mapper::toDomain);
    }

    @Override
    public Maybe<Customer> findById(String id) {
        return Maybe.fromPublisher(repository.findById(id))
                .map(mapper::toDomain);
    }

    @Override
    public Flowable<Customer> findAll() {
        return Flowable.fromPublisher(repository.findAll())
                .map(mapper::toDomain);
    }

    @Override
    public Single<Boolean> existsByEmail(String email) {
        return Single.fromPublisher(repository.existsByEmail(email));
    }

    @Override
    public Single<Boolean> deleteById(String id) {
        return repository.existsById(id)
                .filter(Boolean::booleanValue)
                .flatMap(unused -> repository.deleteById(id).thenReturn(true))
                .defaultIfEmpty(false)
                .as(io.reactivex.rxjava3.core.Single::fromPublisher);
    }
}
