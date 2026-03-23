package com.flash_loan.bank.customer_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String email;
    private String phone;
    private String address;
    private String customerType;
}
