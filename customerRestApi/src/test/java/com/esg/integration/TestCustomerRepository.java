package com.esg.integration;

import com.esg.model.CustomerDetail;
import org.springframework.data.repository.CrudRepository;

public interface TestCustomerRepository extends CrudRepository<CustomerDetail, Integer> {
}
