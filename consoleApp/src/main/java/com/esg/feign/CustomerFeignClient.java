package com.esg.feign;

import com.esg.model.CustomerDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "customer-feign-client", url = "${customer.api.url}")
public interface CustomerFeignClient {
    @PostMapping(value ="/api/customer")
    Iterable<CustomerDetail> postCustomer(@RequestBody List<CustomerDetail> customerDetailsList);
}
