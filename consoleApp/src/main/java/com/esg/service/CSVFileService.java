package com.esg.service;


import com.esg.feign.CustomerFeignClient;
import com.esg.file.reader.CustomerFileReader;
import com.esg.model.CustomerDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CSVFileService {

    @Autowired
    private  CustomerFeignClient customerFeignClient;

    @Autowired
    private CustomerFileReader customerFileReader;

    public List<CustomerDetail> parseCSVFileContent(String filePath) {
        return customerFileReader.parseFileContent(filePath);
    }

    public Iterable<CustomerDetail> saveFileContent(List<CustomerDetail> customerDetailsList) {
        return customerFeignClient.postCustomer(customerDetailsList);
    }
}
