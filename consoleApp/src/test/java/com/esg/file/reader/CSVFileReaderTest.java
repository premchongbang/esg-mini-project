package com.esg.file.reader;

import com.esg.exception.InternalException;
import com.esg.model.CustomerDetail;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVFileReaderTest {

    private final CSVFileReader csvFileReader = new CSVFileReader();

    @Test
    public void parseDataFromCSVTestWithcCorrectFilePath() {
        String filePath = "./src/test/java/com/esg/file/reader/data/customerData.csv";
        List<CustomerDetail> resultLi = csvFileReader.parseFileContent(filePath);
        assertEquals(8, resultLi.size());
    }

    @Test
    public void testParseDataFromCSVWithIncorrectFilePath() {
        String filePath = "./src/test/java/com/esg/file/reader/csvFileReader/customerData.csv";
        InternalException e = assertThrows(InternalException.class, () -> csvFileReader.parseFileContent(filePath));
        assertTrue(e.getMessage().equals("CSV file not found"));
    }
}
