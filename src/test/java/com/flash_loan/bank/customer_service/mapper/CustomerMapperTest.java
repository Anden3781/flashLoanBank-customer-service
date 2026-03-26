package com.flash_loan.bank.customer_service.mapper;

import com.flash_loan.bank.customer_service.dto.BusinessCustomerDto;
import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.dto.PersonalCustomerDto;
import com.flash_loan.bank.customer_service.model.BusinessCustomer;
import com.flash_loan.bank.customer_service.model.Customer;
import com.flash_loan.bank.customer_service.model.PersonalCustomer;
import com.flash_loan.bank.customer_service.model.enums.CustomerType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapper();

    @Test
    void toEntity_PersonalCustomerDto_ShouldMapCorrectly() {
        PersonalCustomerDto dto = new PersonalCustomerDto();
        dto.setId("1");
        dto.setEmail("p@test.com");
        dto.setPhone("999888777");
        dto.setAddress("Street 1");
        dto.setCustomerType("PERSONAL");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setDocumentNumber("12345678");

        Customer entity = mapper.toEntity(dto);
        assertThat(entity).isInstanceOf(PersonalCustomer.class);
        PersonalCustomer pc = (PersonalCustomer) entity;
        assertThat(pc.getType()).isEqualTo(CustomerType.PERSONAL);
        assertThat(pc.getFirstName()).isEqualTo("John");
        assertThat(pc.getDocumentNumber()).isEqualTo("12345678");
    }

    @Test
    void toEntity_BusinessCustomerDto_ShouldMapCorrectly() {
        BusinessCustomerDto dto = new BusinessCustomerDto();
        dto.setId("2");
        dto.setEmail("b@test.com");
        dto.setPhone("999000111");
        dto.setAddress("Street 2");
        dto.setCustomerType("BUSINESS");
        dto.setBusinessName("ACME");
        dto.setRuc("12345678901");

        Customer entity = mapper.toEntity(dto);
        assertThat(entity).isInstanceOf(BusinessCustomer.class);
        BusinessCustomer bc = (BusinessCustomer) entity;
        assertThat(bc.getType()).isEqualTo(CustomerType.BUSINESS);
        assertThat(bc.getBusinessName()).isEqualTo("ACME");
        assertThat(bc.getRuc()).isEqualTo("12345678901");
    }

    @Test
    void toDto_PersonalCustomer_ShouldMapCorrectly() {
        PersonalCustomer pc = PersonalCustomer.builder()
                .id("1")
                .email("p@test.com")
                .phone("999888777")
                .address("Street 1")
                .type(CustomerType.PERSONAL)
                .firstName("John")
                .lastName("Doe")
                .documentNumber("12345678")
                .build();

        CustomerDto dto = mapper.toDto(pc);
        assertThat(dto).isInstanceOf(PersonalCustomerDto.class);
        PersonalCustomerDto out = (PersonalCustomerDto) dto;
        assertThat(out.getCustomerType()).isEqualTo("PERSONAL");
        assertThat(out.getFirstName()).isEqualTo("John");
        assertThat(out.getDocumentNumber()).isEqualTo("12345678");
    }

    @Test
    void toDto_BusinessCustomer_ShouldMapCorrectly() {
        BusinessCustomer bc = BusinessCustomer.builder()
                .id("2")
                .email("b@test.com")
                .phone("999000111")
                .address("Street 2")
                .type(CustomerType.BUSINESS)
                .businessName("ACME")
                .ruc("12345678901")
                .build();

        CustomerDto dto = mapper.toDto(bc);
        assertThat(dto).isInstanceOf(BusinessCustomerDto.class);
        BusinessCustomerDto out = (BusinessCustomerDto) dto;
        assertThat(out.getCustomerType()).isEqualTo("BUSINESS");
        assertThat(out.getBusinessName()).isEqualTo("ACME");
        assertThat(out.getRuc()).isEqualTo("12345678901");
    }

    @Test
    void toEntity_UnknownType_ShouldThrow() {
        CustomerDto unknown = new CustomerDto() {};
        assertThatThrownBy(() -> mapper.toEntity(unknown))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown DTO type");
    }

    @Test
    void toDto_UnknownType_ShouldThrow() {
        Customer unknown = new Customer() {};
        assertThatThrownBy(() -> mapper.toDto(unknown))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown Entity type");
    }
}
