package com.flash_loan.bank.customer_service.infrastructure.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@TypeAlias("personal")
public class PersonalCustomerEntity extends CustomerEntity {
    private String firstName;
    private String lastName;
    private String documentNumber;
}
