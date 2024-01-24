package com.esg.utils;

import com.esg.model.CustomerDetail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerValidatorTest {

    @Test
    public void validateCustomerContentTestWithValidInput() {
        assertTrue(CustomerValidator.validateCustomerContent(new CustomerDetail(null,"XSF1DF","John","15 Norwood","West","Reading","Hampshire","UK","RG12GH")));
        assertTrue(CustomerValidator.validateCustomerContent(new CustomerDetail(null,"XSF1DF","","15 Norwood","West","Reading","Hampshire","UK","RG12GH")));
    }

    @Test
    public void validateCustomerContentTestWithInValidInput() {
        assertFalse(CustomerValidator.validateCustomerContent(new CustomerDetail(null,"","John","15 Norwood","West","Reading","Hampshire","UK","RG12GH")));
        assertFalse(CustomerValidator.validateCustomerContent(new CustomerDetail(null,null,"John","15 Norwood","West","Reading","Hampshire","UK","RG12GH")));
    }
}
