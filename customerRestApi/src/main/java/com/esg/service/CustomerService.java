package com.esg.service;


import com.esg.repository.CustomerRepository;
import com.esg.model.CustomerDetail;
import com.esg.utils.CustomerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Iterable<CustomerDetail> createCustomer(List<CustomerDetail> customerDetailsList) {
        List<CustomerDetail> validatedCustomerList = customerDetailsList.stream().filter(CustomerValidator::validateCustomerContent).toList();
        try {
            return repository.saveAll(validatedCustomerList); // performs batch insert automatically
        } catch (Exception e) {
            log.debug("Error creating customers.", e);
            return Collections.emptyList();
        }
    }

    public Optional<CustomerDetail> getCustomersByRef(String customerRef) {
        return Optional.ofNullable(repository.findCustomerDetailsByCustomerRef(customerRef));
    }
}
