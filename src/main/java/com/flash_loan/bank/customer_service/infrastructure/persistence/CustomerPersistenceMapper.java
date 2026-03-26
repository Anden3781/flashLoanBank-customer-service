package com.flash_loan.bank.customer_service.infrastructure.persistence;

import com.flash_loan.bank.customer_service.model.BusinessCustomer;
import com.flash_loan.bank.customer_service.model.Customer;
import com.flash_loan.bank.customer_service.model.PersonalCustomer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class CustomerPersistenceMapper {

    private final Map<Class<? extends Customer>, Function<Customer, CustomerEntity>> entityMappers = Map.of(
            PersonalCustomer.class, domain -> {
                PersonalCustomer pc = (PersonalCustomer) domain;
                return PersonalCustomerEntity.builder()
                        .id(pc.getId())
                        .email(pc.getEmail())
                        .phone(pc.getPhone())
                        .address(pc.getAddress())
                        .type(pc.getType())
                        .firstName(pc.getFirstName())
                        .lastName(pc.getLastName())
                        .documentNumber(pc.getDocumentNumber())
                        .build();
            },
            BusinessCustomer.class, domain -> {
                BusinessCustomer bc = (BusinessCustomer) domain;
                return BusinessCustomerEntity.builder()
                        .id(bc.getId())
                        .email(bc.getEmail())
                        .phone(bc.getPhone())
                        .address(bc.getAddress())
                        .type(bc.getType())
                        .businessName(bc.getBusinessName())
                        .ruc(bc.getRuc())
                        .build();
            }
    );

    private final Map<Class<? extends CustomerEntity>, Function<CustomerEntity, Customer>> domainMappers = Map.of(
            PersonalCustomerEntity.class, entity -> {
                PersonalCustomerEntity pe = (PersonalCustomerEntity) entity;
                return PersonalCustomer.builder()
                        .id(pe.getId())
                        .email(pe.getEmail())
                        .phone(pe.getPhone())
                        .address(pe.getAddress())
                        .type(pe.getType())
                        .firstName(pe.getFirstName())
                        .lastName(pe.getLastName())
                        .documentNumber(pe.getDocumentNumber())
                        .build();
            },
            BusinessCustomerEntity.class, entity -> {
                BusinessCustomerEntity be = (BusinessCustomerEntity) entity;
                return BusinessCustomer.builder()
                        .id(be.getId())
                        .email(be.getEmail())
                        .phone(be.getPhone())
                        .address(be.getAddress())
                        .type(be.getType())
                        .businessName(be.getBusinessName())
                        .ruc(be.getRuc())
                        .build();
            }
    );

    public CustomerEntity toEntity(Customer domain) {
        return Optional.ofNullable(domain)
                .map(d -> entityMappers.get(d.getClass()))
                .map(mapper -> mapper.apply(domain))
                .orElse(null);
    }

    public Customer toDomain(CustomerEntity entity) {
        return Optional.ofNullable(entity)
                .map(e -> domainMappers.get(e.getClass()))
                .map(mapper -> mapper.apply(entity))
                .orElse(null);
    }
}
