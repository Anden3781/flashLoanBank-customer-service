package com.flash_loan.bank.customer_service.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME, 
    include = JsonTypeInfo.As.EXISTING_PROPERTY, 
    property = "customerType",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PersonalCustomerDto.class, name = "PERSONAL"),
    @JsonSubTypes.Type(value = BusinessCustomerDto.class, name = "BUSINESS")
})
public abstract class CustomerDto {

    private String id;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
    private String phone;

    private String address;

    @NotBlank(message = "El tipo de cliente es obligatorio (PERSONAL o BUSINESS)")
    private String customerType;
}
