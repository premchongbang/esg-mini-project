package com.esg.file.reader;

import com.esg.exception.InternalException;
import com.esg.model.CustomerDetail;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CSVFileReader implements CustomerFileReader{

    public List<CustomerDetail> parseFileContent(String csvFilePath) {
        List<CustomerDetail> customerDetailsList = new ArrayList<>();

        try(
            CSVReader reader = new CSVReaderBuilder(new FileReader(csvFilePath))
                                    .withSkipLines(1) // skipping heading
                    .build()) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                customerDetailsList.add(new CustomerDetail(
                        null,
                        nextLine[0],
                        nextLine[1],
                        nextLine[2],
                        nextLine[3],
                        nextLine[4],
                        nextLine[5],
                        nextLine[6],
                        nextLine[7])
                );
            }
        } catch (FileNotFoundException e) {
            throw new InternalException("CSV file not found", e);
        } catch (CsvValidationException e) {
            throw new InternalException("CSV validation failed", e);
        } catch (Exception e) {
            throw new InternalException("Error while reading CSV file", e);
        }

        return customerDetailsList;
    }
}
