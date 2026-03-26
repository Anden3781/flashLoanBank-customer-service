package com.flash_loan.bank.customer_service.mapper;

import com.flash_loan.bank.customer_service.dto.BusinessCustomerDto;
import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.dto.PersonalCustomerDto;
import com.flash_loan.bank.customer_service.model.BusinessCustomer;
import com.flash_loan.bank.customer_service.model.Customer;
import com.flash_loan.bank.customer_service.model.PersonalCustomer;
import com.flash_loan.bank.customer_service.model.enums.CustomerType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class CustomerMapper {

    private final Map<Class<? extends CustomerDto>, Supplier<Customer>> entityFactories = Map.of(
            PersonalCustomerDto.class, () -> {
                PersonalCustomer pc = new PersonalCustomer();
                pc.setType(CustomerType.PERSONAL);
                return pc;
            },
            BusinessCustomerDto.class, () -> {
                BusinessCustomer bc = new BusinessCustomer();
                bc.setType(CustomerType.BUSINESS);
                return bc;
            }
    );

    private final Map<Class<? extends Customer>, Supplier<CustomerDto>> dtoFactories = Map.of(
            PersonalCustomer.class, () -> {
                PersonalCustomerDto dto = new PersonalCustomerDto();
                dto.setCustomerType(CustomerType.PERSONAL.name());
                return dto;
            },
            BusinessCustomer.class, () -> {
                BusinessCustomerDto dto = new BusinessCustomerDto();
                dto.setCustomerType(CustomerType.BUSINESS.name());
                return dto;
            }
    );

    public Customer toEntity(CustomerDto dto) {
        return Optional.ofNullable(dto)
                .map(d -> entityFactories.get(d.getClass()))
                .map(Supplier::get)
                .map(customer -> {
                    BeanUtils.copyProperties(dto, customer);
                    return customer;
                })
                .orElseThrow(() -> new IllegalArgumentException("Unknown DTO type: " + (dto != null ? dto.getClass() : "null")));
    }

    public CustomerDto toDto(Customer entity) {
        return Optional.ofNullable(entity)
                .map(e -> dtoFactories.get(e.getClass()))
                .map(Supplier::get)
                .map(dto -> {
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .orElseThrow(() -> new IllegalArgumentException("Unknown Entity type: " + (entity != null ? entity.getClass() : "null")));
    }
}