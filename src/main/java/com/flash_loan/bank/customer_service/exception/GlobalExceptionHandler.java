package com.flash_loan.bank.customer_service.exception;

import com.flash_loan.bank.customer_service.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiError> handleCustomerNotFound(CustomerNotFoundException ex, ServerHttpRequest request) {
        ApiError apiError = buildError(HttpStatus.NOT_FOUND, "Cliente no encontrado", ex.getMessage(), request.getPath().value());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleCustomerAlreadyExists(CustomerAlreadyExistsException ex, ServerHttpRequest request) {
        ApiError apiError = buildError(HttpStatus.CONFLICT, "Conflicto", ex.getMessage(), request.getPath().value());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ApiError> handleBusinessValidation(BusinessValidationException ex, ServerHttpRequest request) {
        ApiError apiError = buildError(HttpStatus.valueOf(422), "Error de validación de negocio", ex.getMessage(), request.getPath().value());
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(422));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(WebExchangeBindException ex, ServerHttpRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse(ex.getMessage());
        
        ApiError apiError = buildError(HttpStatus.BAD_REQUEST, "Error de validación de entrada", errorMessage, request.getPath().value());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex, ServerHttpRequest request) {
        ApiError apiError = buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", ex.getMessage(), request.getPath().value());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiError buildError(HttpStatus status, String error, String message, String path) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .build();
    }
}
