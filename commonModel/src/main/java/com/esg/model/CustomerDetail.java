package com.esg.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

public record CustomerDetail(@Id Integer id, @NotEmpty String customerRef, String customerName, String addressLine1, String addressLine2, String town, String county, String country, String postcode) {
}