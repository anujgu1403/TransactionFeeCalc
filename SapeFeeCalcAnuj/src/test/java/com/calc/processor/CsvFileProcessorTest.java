package com.calc.processor;

import com.calc.enumeration.TransactionType;
import com.calc.model.TransactionsFeed;
import com.calc.util.FeeCalcUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author anujkumar
 *
 * Processor to test csvFileProcessor
 *
 */
@SpringBootTest
public class CsvFileProcessorTest {

    @MockBean
    FileProcessor csvFileProcessor;

    @Test
    public void readTransactionsFeedTest() throws IOException {
        String inputFileName = "/Users/anukumar/Desktop/personal/sample_input.csv";

        List<TransactionsFeed> expectedList = spy(getTransactionsList());
        when(csvFileProcessor.readTransactionsFeed(any())).thenReturn(expectedList);

        List<TransactionsFeed> actualList = csvFileProcessor.readTransactionsFeed(inputFileName);
        Assertions.assertEquals(expectedList.size(), actualList.size(), "Transactions list should have one entry.");
    }

    @Test
    public void writeTransactionsFeedTest() throws IOException {
        String outputFileName = "/Users/anukumar/Desktop/personal/sample_output.csv";

        List<TransactionsFeed> expectedList = spy(getTransactionsList());
        csvFileProcessor.writeTransactionSummaryReport(expectedList, outputFileName);
        File outputFile = new File(outputFileName);
        Assertions.assertTrue(outputFile.exists(), "After calculating transactions processing fee, output file should exists.");
    }

    private List<TransactionsFeed> getTransactionsList(){
        List<TransactionsFeed> transactionsList = new ArrayList<>();
        TransactionsFeed transaction= new TransactionsFeed();
        transaction.setExtTransactionId("SAPEXTXN1");
        transaction.setClientId("GS");
        transaction.setSecurityId("ICICI");
        transaction.setTransactionType(TransactionType.BUY);
        transaction.setTransactionDate(FeeCalcUtil.parseDate("23/11/13"));
        transaction.setMarketValue(109.5);
        transaction.setPriorityFlag(true);
        transactionsList.add(transaction);
        return transactionsList;
    }
}
