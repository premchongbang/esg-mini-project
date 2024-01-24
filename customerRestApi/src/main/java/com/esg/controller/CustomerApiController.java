package com.esg.controller;

import com.esg.exception.CustomerNotFoundException;
import com.esg.model.CustomerDetail;
import com.esg.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/customer")
public class CustomerApiController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<CustomerDetail> saveCustomerList(@RequestBody @NotEmpty List<@Valid CustomerDetail> customerDetailsList) {
        return customerService.createCustomer(customerDetailsList);
    }

    @GetMapping("/{customerRef}")
    public CustomerDetail getCustomersByRef(@PathVariable String customerRef) {
        Optional<CustomerDetail> result = customerService.getCustomersByRef(customerRef);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new CustomerNotFoundException();
        }
    }
}
