package com.flash_loan.bank.customer_service.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeAlias("BUSINESS")
public class BusinessCustomer extends Customer {

    private String businessName;
    private String ruc;

}
