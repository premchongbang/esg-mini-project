package com.esg.file.reader;

import com.esg.model.CustomerDetail;

import java.util.List;

public interface CustomerFileReader {
    List<CustomerDetail> parseFileContent(String csvFilePath);
}
