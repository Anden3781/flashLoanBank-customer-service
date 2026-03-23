package com.flash_loan.bank.customer_service.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.TypeAlias;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeAlias("INDIVIDUAL")
public class IndividualCustomer extends Customer {

    private String firstName;
    private String lastName;
    private String documentNumber;

}
