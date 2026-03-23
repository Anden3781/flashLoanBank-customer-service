package com.flash_loan.bank.customer_service.service;

import com.flash_loan.bank.customer_service.dto.CustomerDto;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface CustomerService {

    Single<CustomerDto> createCustomer(CustomerDto customerDto);
    Maybe<CustomerDto> getCustomerById(String id);
    Observable<CustomerDto> getAllCustomers();

}
