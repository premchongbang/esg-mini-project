package com.esg.integration;

import com.esg.model.CustomerDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
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

    @Value("${esg.basic.auth.username}")
    private String username;

    @Value("${esg.basic.auth.password}")
    private String password;
    HttpHeaders headers = new HttpHeaders();

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        String base64Credentials = Base64.getEncoder().encodeToString(credentialsBytes);

        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + base64Credentials);

        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().addAll(headers);
            return execution.execute(request, body);
        });

        baseUrl.append(":").append(port).append("/api/customer");
        testData = List.of(
                new CustomerDetail(null,"XSF1DF","John","15 Norwood","West","Reading","Hampshire","UK","RG12GH"),
                new CustomerDetail(null,"ABF2DF","Jeff","15 Norwood","West","Reading","Hampshire","UK","RG13GH")
        );
        testCustomerRepository.deleteAll();
    }

    @Test
    public void saveCustomerListApiTest() {
        Iterable<CustomerDetail> itr = restTemplate.postForObject(baseUrl.toString(), testData, Iterable.class);
        assert itr != null;
        assertEquals(2, itr.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void getCustomerbyRefApiTest() {
        testCustomerRepository.saveAll(testData);
        String getUrl = baseUrl.toString().concat("/XSF1DF");
        CustomerDetail cd = restTemplate.getForObject(getUrl, CustomerDetail.class);
        assert cd != null;
        assertEquals("John", cd.customerName());
    }
}
