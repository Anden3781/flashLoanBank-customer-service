package com.flash_loan.bank.customer_service.infrastructure.persistence;

import com.flash_loan.bank.customer_service.model.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customers")
public class CustomerEntity {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
    private String phone;
    private String address;
    private CustomerType type;
    
    // Spring Data MongoDB needs a way to distinguish between Personal and Business if we use inheritance
    private String _class; 
}
