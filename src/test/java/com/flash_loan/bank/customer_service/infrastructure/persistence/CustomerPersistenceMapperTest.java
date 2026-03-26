package com.flash_loan.bank.customer_service.infrastructure.persistence;

import com.flash_loan.bank.customer_service.model.BusinessCustomer;
import com.flash_loan.bank.customer_service.model.PersonalCustomer;
import com.flash_loan.bank.customer_service.model.enums.CustomerType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerPersistenceMapperTest {

    private final CustomerPersistenceMapper mapper = new CustomerPersistenceMapper();

    @Test
    void toEntity_and_toDomain_Personal_ShouldPreserveFields() {
        PersonalCustomer domain = PersonalCustomer.builder()
                .id("1")
                .email("p@test.com")
                .phone("999888777")
                .address("Street 1")
                .type(CustomerType.PERSONAL)
                .firstName("John")
                .lastName("Doe")
                .documentNumber("12345678")
                .build();

        CustomerEntity entity = mapper.toEntity(domain);
        assertThat(entity).isInstanceOf(PersonalCustomerEntity.class);
        PersonalCustomerEntity pe = (PersonalCustomerEntity) entity;
        assertThat(pe.getFirstName()).isEqualTo("John");
        assertThat(pe.getDocumentNumber()).isEqualTo("12345678");

        // Back to domain
        PersonalCustomer back = (PersonalCustomer) mapper.toDomain(pe);
        assertThat(back.getId()).isEqualTo("1");
        assertThat(back.getFirstName()).isEqualTo("John");
        assertThat(back.getType()).isEqualTo(CustomerType.PERSONAL);
    }

    @Test
    void toEntity_and_toDomain_Business_ShouldPreserveFields() {
        BusinessCustomer domain = BusinessCustomer.builder()
                .id("2")
                .email("b@test.com")
                .phone("999000111")
                .address("Street 2")
                .type(CustomerType.BUSINESS)
                .businessName("ACME")
                .ruc("12345678901")
                .build();

        CustomerEntity entity = mapper.toEntity(domain);
        assertThat(entity).isInstanceOf(BusinessCustomerEntity.class);
        BusinessCustomerEntity be = (BusinessCustomerEntity) entity;
        assertThat(be.getBusinessName()).isEqualTo("ACME");
        assertThat(be.getRuc()).isEqualTo("12345678901");

        // Back to domain
        BusinessCustomer back = (BusinessCustomer) mapper.toDomain(be);
        assertThat(back.getId()).isEqualTo("2");
        assertThat(back.getBusinessName()).isEqualTo("ACME");
        assertThat(back.getType()).isEqualTo(CustomerType.BUSINESS);
    }

    @Test
    void toEntity_Null_ReturnsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toDomain_Null_ReturnsNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }
}
