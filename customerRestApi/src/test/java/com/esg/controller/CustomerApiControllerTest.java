package com.esg.controller;

import com.esg.exception.CustomerNotFoundException;
import com.esg.model.CustomerDetail;
import com.esg.security.SecurityConfig;
import com.esg.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerApiController.class)
@AutoConfigureMockMvc
@Import({SecurityConfig.class})
public class CustomerApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    List<CustomerDetail> testData = new ArrayList<>();

    @Value("${esg.basic.auth.username}")
    private String username;

    @Value("${esg.basic.auth.password}")
    private String password;

    @BeforeEach
    void setUp() {
        testData = List.of(
                new CustomerDetail(null,"XSF1DF","John","15 Norwood","West","Reading","Hampshire","UK","RG12GH"),
                new CustomerDetail(null,"XSF2DF","Jeff","15 Norwood","West","Reading","Hampshire","UK","RG13GH")
        );
    }

    @Test
    public void postMappingSaveCustomerListTest() throws Exception {
        String jsonResponse = """
                    [
                        {
                            "id" : 1,
                            "customerRef": "XSF1DF",
                            "customerName": "John",
                            "addressLine1": "15 Norwood",
                            "addressLine2": "West",
                            "town": "Reading",
                            "county":"Hampshire",
                            "country": "UK",
                            "postcode": "RG12GH"
                        },
                         {
                            "id" : 2,
                            "customerRef": "XSF2DF",
                            "customerName": "Jeff",
                            "addressLine1": "15 Norwood",
                            "addressLine2": "West",
                            "town": "Reading",
                            "county":"Hampshire",
                            "country": "UK",
                            "postcode": "RG13GH"
                        }
                    ]
                """;
        Iterable<CustomerDetail> responseList = List.of(
                new CustomerDetail(1,"XSF1DF","John","15 Norwood","West","Reading","Hampshire","UK","RG12GH"),
                new CustomerDetail(2,"XSF2DF","Jeff","15 Norwood","West","Reading","Hampshire","UK","RG13GH")
        );

        when(customerService.createCustomer(testData)).thenReturn(responseList);

        String jsonContent = new ObjectMapper().writeValueAsString(testData);
        mockMvc.perform(post("/api/customer")
                            .with(httpBasic(username, password))
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    public void postMappingSaveCustomerListTestWithEmptyInput() throws Exception {
        mockMvc.perform(post("/api/customer")
                            .with(httpBasic(username, password))
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(customerService,times(0)).createCustomer(testData);
    }

    @Test
    public void getMappingGetCustomersByRefTestWithValidRef() throws Exception {
        String jsonResponse = """
                
                    {
                        "id" : 1,
                        "customerRef": "XSF1DF",
                        "customerName": "John",
                        "addressLine1": "15 Norwood",
                        "addressLine2": "West",
                        "town": "Reading",
                        "county":"Hampshire",
                        "country": "UK",
                        "postcode": "RG12GH"
                    }
                """;
        when(customerService.getCustomersByRef("XSF1DF")).thenReturn(Optional.of(new CustomerDetail(1,"XSF1DF","John","15 Norwood","West","Reading","Hampshire","UK","RG12GH")));
        mockMvc.perform(get("/api/customer/XSF1DF")
                        .accept(MediaType.APPLICATION_JSON).with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse, false));
    }

    @Test
    public void getMappingGetCustomersByRefTestWithInvalidRef() throws Exception {
        when(customerService.getCustomersByRef("XSF1D")).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(get("/api/customer/XSF1D")
                            .with(httpBasic(username, password))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMappingGetCustomersByRefTestWithoutAuthentication() throws Exception {
        when(customerService.getCustomersByRef("XSF1D")).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(get("/api/customer/XSF1D")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
