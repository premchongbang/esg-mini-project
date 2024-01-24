package com.esg.integration;

import com.esg.model.CustomerDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 we will be using the same embedded H2 database for integration test
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerApiIntegrationTest {

    @LocalServerPort
    private int port;

    private StringBuilder baseUrl= new StringBuilder("http://localhost");
    private static RestTemplate restTemplate;

    @Autowired
    TestCustomerRepository testCustomerRepository;

    List<CustomerDetail> testData = new ArrayList<>();

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.append(":").append(port).append("/api/customer");
        testData = List.of(
                new CustomerDetail(null,"XSF1DF","John","15 Norwood","West","Reading","Hampshire","UK","RG12GH"),
                new CustomerDetail(null,"ABF2DF","Jeff","15 Norwood","West","Reading","Hampshire","UK","RG13GH")
        );
        testCustomerRepository.deleteAll();
    }

    @Test
    public void saveCustomerListApiTest() {
        Iterable<CustomerDetail> itr = restTemplate.postForObject(baseUrl.toString(), testData, Iterable.class);
        assertEquals(2, itr.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void getCustomerbyRefApiTest() {
        testCustomerRepository.saveAll(testData);
        String getUrl = baseUrl.toString().concat("/XSF1DF");
        CustomerDetail cd = restTemplate.getForObject(getUrl, CustomerDetail.class);
        assertEquals("John", cd.customerName());
    }
}
