package com.calc.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.calc.common.Constants;
import com.calc.model.TransactionsFeed;
import com.calc.util.FeeCalcUtil;
import com.calc.configuration.FileConfigProperties;

/**
 * @author anujkumar
 *
 * Processor to process the CSV files
 *
 */

@Component
public class CsvFileProcessor implements FileProcessor {

    @Autowired
    FileConfigProperties configProperties;

    /**
     * method to read the transactions from CSV file and convert into transaction list
     *
     * @param inputFileName
     * @return List<TransactionsFeed>
     *
     * */
    @Override
    public List<TransactionsFeed> readTransactionsFeed(String inputFileName) throws IOException {

        List<TransactionsFeed> transactionsList = new ArrayList<>();
        BufferedReader reader = null;
        if (!StringUtils.isEmpty(inputFileName)) {
            try {
                 reader = new BufferedReader(new FileReader(inputFileName));

                //To skip the header of input csv file
                reader.readLine();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");

                    //To parse the input into model class
                    TransactionsFeed transaction = FeeCalcUtil.parseStringArrayToModel(data);

                    //To process the transaction, calculate the processing fee and add into transaction list
                    transactionsList.add(FeeCalcUtil.calculateTransactionProcessingFee(transactionsList, transaction));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                reader.close();
            }
        }
        return transactionsList;
    }

    /**
     * method to write the transactions into CSV file after processing fee calculation
     *
     * @param transactionsList
     * @param outputFileName
     *
     * */
    @Override
    public void writeTransactionSummaryReport(List<TransactionsFeed> transactionsList, String outputFileName) throws IOException {

        if (!StringUtils.isEmpty(outputFileName)) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));

            //To add the header in output file
            bufferedWriter.write(Constants.OUTPUT_FILE_HEADER);

            //To sort the transactions based on clientId, transactionType, transactionDate and priorityFlag
            Collections.sort(transactionsList, new TransactionsFeed());
            transactionsList.stream().forEach(transactionsFeed -> {
                        try {

                            //To convert TransactionFeed model to String and writing in output file
                            bufferedWriter.write("\n" + FeeCalcUtil.parseModelToString(transactionsFeed));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            System.out.println("Transaction processed successfully.");
            bufferedWriter.close();
        }
    }
}
