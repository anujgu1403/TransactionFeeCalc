package com.calc.executor;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.calc.configuration.FileConfigProperties;
import com.calc.model.TransactionsFeed;
import com.calc.processor.FileProcessor;
import com.calc.processor.FileProcessorFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author anujkumar
 *
 * CommandLineRunner to run the transaction fee calculator
 *
 */

@Configuration
public class TransactionFeeCalcExecutor implements CommandLineRunner {

    @Autowired
    FileConfigProperties configProperties;

    @Autowired
    FileProcessorFactory fileProcessorFactory;

    /**
     * method to run the commandLineRunner
     *
     * @param args
     * 
     * */
    public void run(String... args) throws Exception{

        //To get input fileType
        String fileType = configProperties.getInputFileExtn();

        //To form the input file location using configurable properties
        String inputFileName = configProperties.getFileLocation()+configProperties.getInputFileName()+"."+configProperties.getInputFileExtn();

        //To form the output file location using configurable properties
        String outputFileName = configProperties.getFileLocation()+configProperties.getOutputFileName()+"."+configProperties.getOutputFileExtn();

        //To get the file processor which based on type of file need to be processed
        FileProcessor fileProcessor = fileProcessorFactory.getFileProcessor(fileType);

        //To call the fileProcessor to process the transactions input and calculate processing fee
        List<TransactionsFeed>  transactionsList = fileProcessor.readTransactionsFeed(inputFileName);

        //To call fileProcessor to write transaction summary
        fileProcessor.writeTransactionSummaryReport(transactionsList, outputFileName);
    }
}
