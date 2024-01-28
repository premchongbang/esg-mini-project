package com.esg;

import com.esg.exception.InternalException;
import com.esg.model.CustomerDetail;
import com.esg.service.CSVFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class RunConsoleApplication {

    @Autowired
    CSVFileService csvFileService;

    public static void main(String[] args) {
        SpringApplication.run(RunConsoleApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            String userInput;
            System.out.println("Welcome to console app for loading the customer details.");
            System.out.println("Provide CSV file path:");
            Scanner scanner = new Scanner(System.in);

            while(true) {
                userInput = scanner.nextLine();
                try {
                    List<CustomerDetail> customerDetailsList = csvFileService.parseCSVFileContent(userInput);
                    if (!customerDetailsList.isEmpty()) {
                        long numberOfSavedCustomerRecord = csvFileService.saveFileContent(customerDetailsList).spliterator().getExactSizeIfKnown();
                        System.out.printf("Total %d customer record saved out of %d.\n", numberOfSavedCustomerRecord, customerDetailsList.size());
                    } else {
                        System.out.println("File content is empty or invalid.");
                    }
                    System.out.println("Provide file path:");
                } catch (Exception e) {
                    System.out.printf("%s.\nPlease provide correct file path:\n", e.getMessage());
                }
            }
        };
    }
}