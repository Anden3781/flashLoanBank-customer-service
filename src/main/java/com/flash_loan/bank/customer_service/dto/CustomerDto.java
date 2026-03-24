package com.flash_loan.bank.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class CustomerDto {

    private String id;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    @Indexed(unique = true)
    private String email;
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
    private String phone;

    private String address;
    @NotBlank(message = "El tipo de cliente es obligatorio")
    private String customerType; // INDIVIDUAL o BUSINESS

    // Individual

    private String firstName;
    private String lastName;
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    private String documentNumber;

    // Business

    private String businessName;
    private String ruc;
}
