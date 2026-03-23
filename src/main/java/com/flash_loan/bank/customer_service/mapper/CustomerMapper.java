package com.flash_loan.bank.customer_service.mapper;

import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.model.BusinessCustomer;
import com.flash_loan.bank.customer_service.model.Customer;
import com.flash_loan.bank.customer_service.model.IndividualCustomer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDto dto) {
        if ("INDIVIDUAL".equalsIgnoreCase(dto.getCustomerType())) {
            IndividualCustomer customer = new IndividualCustomer();
            BeanUtils.copyProperties(dto, customer);
            return customer;
        } else {
            BusinessCustomer customer = new BusinessCustomer();
            BeanUtils.copyProperties(dto, customer);
            return customer;
        }
    }

    public CustomerDto toDto(Customer entity) {
        CustomerDto dto = new CustomerDto();
        BeanUtils.copyProperties(entity, dto);

        if (entity instanceof IndividualCustomer) {
            IndividualCustomer ind = (IndividualCustomer) entity;
            dto.setFirstName(ind.getFirstName());
            dto.setLastName(ind.getLastName());
            dto.setDocumentNumber(ind.getDocumentNumber());
        } else if (entity instanceof BusinessCustomer) {
            BusinessCustomer bus = (BusinessCustomer) entity;
            dto.setBusinessName(bus.getBusinessName());
            dto.setRuc(bus.getRuc());
        }
        return dto;
    }
}