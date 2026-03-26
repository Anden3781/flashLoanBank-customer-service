package com.flash_loan.bank.customer_service.model;

import com.flash_loan.bank.customer_service.model.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Customer {
    private String id;
    private String email;
    private String phone;
    private String address;
    private CustomerType type;
}
