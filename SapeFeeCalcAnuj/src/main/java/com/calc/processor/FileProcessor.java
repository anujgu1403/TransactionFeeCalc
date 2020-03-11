package com.calc.processor;

import java.io.IOException;
import java.util.List;
import com.calc.model.TransactionsFeed;

/**
 * @author anujkumar
 *
 * Base File Processor to declare the common fileProcessor operations
 *
 */
public interface FileProcessor {

    /**
     * method declaration for read operation of fileProcessor
     *
     * @param inputFileName
     * @return List<TransactionsFeed>
     *
     * */
    List<TransactionsFeed> readTransactionsFeed(String inputFileName) throws IOException;

    /**
     * method declaration for write operation of fileProcessor
     *
     * @param transactionsList
     * @param outputFileName
     *
     * */
    void writeTransactionSummaryReport(List<TransactionsFeed> transactionsList, String outputFileName) throws IOException;
}
