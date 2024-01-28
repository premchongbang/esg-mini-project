package com.esg.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class BasicAuth {

    @Value("${customer.api.username}")
    private String username;

    @Value("${customer.api.password}")
    private String password;

    public String createBasicAuthString() {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes();
        return "Basic " + Base64.getEncoder().encodeToString(credentialsBytes);
    }
}
