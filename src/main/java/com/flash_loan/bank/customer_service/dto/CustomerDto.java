package com.flash_loan.bank.customer_service.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private String id;
    private String email;
    private String phone;
    private String address;
    private String customerType;

    // Individual

    private String firstName;
    private String lastName;
    private String documentNumber;

    // Business

    private String businessName;
    private String ruc;

}
