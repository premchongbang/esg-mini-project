package com.esg.repository;

import com.esg.model.CustomerDetail;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerDetail, Integer> {

    CustomerDetail findCustomerDetailsByCustomerRef(String customerRef);
}
