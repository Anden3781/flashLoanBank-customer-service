package com.flash_loan.bank.customer_service.service.impl;

import com.flash_loan.bank.customer_service.dto.CustomerDto;
import com.flash_loan.bank.customer_service.dto.PersonalCustomerDto;
import com.flash_loan.bank.customer_service.exception.CustomerAlreadyExistsException;
import com.flash_loan.bank.customer_service.mapper.CustomerMapper;
import com.flash_loan.bank.customer_service.model.Customer;
import com.flash_loan.bank.customer_service.model.PersonalCustomer;
import com.flash_loan.bank.customer_service.repository.CustomerRepositoryPort;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepositoryPort repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private PersonalCustomerDto customerDto;
    private PersonalCustomer customer;

    @BeforeEach
    void setUp() {
        customerDto = new PersonalCustomerDto();
        customerDto.setId("1");
        customerDto.setEmail("test@test.com");
        customerDto.setFirstName("John");
        customerDto.setLastName("Doe");
        customerDto.setDocumentNumber("12345678");
        customerDto.setPhone("987654321");
        customerDto.setCustomerType("PERSONAL");

        customer = new PersonalCustomer();
        customer.setId("1");
        customer.setEmail("test@test.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setDocumentNumber("12345678");
        customer.setPhone("987654321");
    }

    @Test
    void createCustomer_WhenEmailDoesNotExist_ShouldReturnCreatedCustomer() {
        when(repository.existsByEmail(anyString())).thenReturn(Single.just(false));
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(customer);
        when(repository.save(any(Customer.class))).thenReturn(Single.just(customer));
        when(mapper.toDto(any(Customer.class))).thenReturn(customerDto);

        TestObserver<CustomerDto> testObserver = customerService.createCustomer(customerDto).test();

        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValue(dto -> dto.getEmail().equals(customerDto.getEmail()));
        verify(repository).save(any(Customer.class));
    }

    @Test
    void createCustomer_WhenEmailExists_ShouldThrowException() {
        when(repository.existsByEmail(anyString())).thenReturn(Single.just(true));

        TestObserver<CustomerDto> testObserver = customerService.createCustomer(customerDto).test();

        testObserver.assertError(CustomerAlreadyExistsException.class);
        verify(repository, never()).save(any(Customer.class));
    }

    @Test
    void getCustomerById_WhenExists_ShouldReturnCustomer() {
        when(repository.findById(anyString())).thenReturn(Maybe.just(customer));
        when(mapper.toDto(any(Customer.class))).thenReturn(customerDto);

        TestObserver<CustomerDto> testObserver = customerService.getCustomerById("1").test();

        testObserver.assertComplete();
        testObserver.assertValue(dto -> dto.getId().equals("1"));
    }

    @Test
    void getCustomerById_WhenNotExists_ShouldReturnEmpty() {
        when(repository.findById(anyString())).thenReturn(Maybe.empty());

        TestObserver<CustomerDto> testObserver = customerService.getCustomerById("1").test();

        testObserver.assertComplete();
        testObserver.assertNoValues();
    }

    @Test
    void getAllCustomers_ShouldReturnFlowable() {
        when(repository.findAll()).thenReturn(Flowable.just(customer));
        when(mapper.toDto(any(Customer.class))).thenReturn(customerDto);

        TestSubscriber<CustomerDto> testSubscriber = customerService.getAllCustomers().test();

        testSubscriber.assertComplete();
        testSubscriber.assertValueCount(1);
    }

    @Test
    void updateCustomer_WhenExists_ShouldReturnUpdatedCustomer() {
        when(repository.findById(anyString())).thenReturn(Maybe.just(customer));
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(customer);
        when(repository.save(any(Customer.class))).thenReturn(Single.just(customer));
        when(mapper.toDto(any(Customer.class))).thenReturn(customerDto);

        TestObserver<CustomerDto> testObserver = customerService.updateCustomer("1", customerDto).test();

        testObserver.assertComplete();
        testObserver.assertValue(dto -> dto.getId().equals("1"));
    }

    @Test
    void updateCustomer_WhenNotExists_ShouldThrowException() {
        when(repository.findById(anyString())).thenReturn(Maybe.empty());

        TestObserver<CustomerDto> testObserver = customerService.updateCustomer("1", customerDto).test();

        testObserver.assertError(RuntimeException.class);
    }

    @Test
    void deleteCustomer_WhenExists_ShouldComplete() {
        when(repository.deleteById(anyString())).thenReturn(Single.just(true));

        customerService.deleteCustomer("1").test()
                .assertComplete()
                .assertNoErrors();
    }

    @Test
    void deleteCustomer_WhenNotExists_ShouldThrowException() {
        when(repository.deleteById(anyString())).thenReturn(Single.just(false));

        customerService.deleteCustomer("1").test()
                .assertError(RuntimeException.class);
    }
}
