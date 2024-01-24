package com.esg.utils;

import com.esg.model.CustomerDetail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class CustomerValidator {
    public static boolean validateCustomerContent(CustomerDetail customerDetail) {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        Set<ConstraintViolation<CustomerDetail>> violations = validator.validate(customerDetail);
        return violations.isEmpty();
    }
}
