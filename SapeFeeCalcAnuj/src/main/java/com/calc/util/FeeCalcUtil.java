package com.calc.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.calc.common.Constants;
import com.calc.model.TransactionsFeed;
import com.calc.enumeration.TransactionType;

/**
 *
 * @author anujkumar
 *
 * Common util class used to define common helper methods
 * */
public class FeeCalcUtil {

    /**
     *
     * method to calculate the transaction processing fee
     * @param transactionsList
     * @param transaction
     *
     * @return TransactionsFeed
     */
    public static TransactionsFeed calculateTransactionProcessingFee(List<TransactionsFeed> transactionsList, TransactionsFeed transaction){
        if(isIntraDayTransaction(transactionsList, transaction)){
            transaction.setProcessingFee(Constants.INFRADAY_PROCESSING_FEE);
        }else{
            if(transaction.getPriorityFlag()){
                transaction.setProcessingFee(Constants.HIGH_PRIORITY_PROCESSING_FEE);
            }
            if((transaction.getTransactionType().equals(TransactionType.SELL) ||
                    transaction.getTransactionType().equals(TransactionType.WITHDRAW)) && !transaction.getPriorityFlag()){
                transaction.setProcessingFee(Constants.NORMAL_SELL_PROCESSING_FEE);
            }
            if((transaction.getTransactionType().equals(TransactionType.BUY) ||
                    transaction.getTransactionType().equals(TransactionType.DEPOSIT)) && !transaction.getPriorityFlag()){
                transaction.setProcessingFee(Constants.NORMAL_BUY_PROCESSING_FEE);
            }
        }
        return transaction;
    }

    /**
     *
     * method to check if transaction is intraday
     * @param transactionsList
     * @param transaction
     *
     * @return Boolean
     */
    public static boolean isIntraDayTransaction(List<TransactionsFeed> transactionsList, TransactionsFeed transaction) {
        boolean isIntraDayTransaction = false;
        TransactionsFeed temp= transactionsList.stream()
                .filter(transactionsFeed -> (transactionsFeed.getClientId().equals(transaction.getClientId())
                && transactionsFeed.getSecurityId().equals(transaction.getSecurityId())
                && transactionsFeed.getTransactionDate().equals(transaction.getTransactionDate())))
                .filter(transactionsFeed -> (transactionsFeed.getTransactionType().equals(TransactionType.BUY)
                && transaction.getTransactionType().equals(TransactionType.SELL)) ||
                 transactionsFeed.getTransactionType().equals(TransactionType.SELL)
                && transaction.getTransactionType().equals(TransactionType.BUY))
        .findAny().orElse(null);
        if(temp!=null){
            isIntraDayTransaction = true;
            transactionsList.remove(temp);
            temp.setProcessingFee(Constants.INFRADAY_PROCESSING_FEE);
            transactionsList.add(temp);
        }
        return isIntraDayTransaction;
    }

    /**
     *
     * method to parse date string to date
     * @param date
     *
     * @return Date
     */
    public static Date parseDate(String date) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");
            Date convertedDate = sdf.parse(date);
            return convertedDate;
        }catch(Exception  ex){
            return null;
        }
    }

    /**
     *
     * method to parse date to string
     * @param date
     *
     * @return String
     */
    public static String parseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");
        return sdf.format(date);
    }

    /**
     *
     * method to parse model to string
     * @param transaction
     *
     * @return String
     */
    public static String parseModelToString(TransactionsFeed transaction){
        return  transaction.getClientId() + "," +
                transaction.getTransactionType().getTransactionName() + "," + FeeCalcUtil.parseDate(transaction.getTransactionDate()) + "," + (
                transaction.getPriorityFlag() ? "Y" : "N") + "," + transaction.getProcessingFee();
    }

    /**
     *
     * method to parse string array to model
     * @param data
     *
     * @return TransactionsFeed
     */
    public static TransactionsFeed parseStringArrayToModel(String []data){
        TransactionsFeed transaction= new TransactionsFeed();
        transaction.setExtTransactionId(data[0]);
        transaction.setClientId(data[1]);
        transaction.setSecurityId(data[2]);
        transaction.setTransactionType(TransactionType.valueOf(data[3]));
        transaction.setTransactionDate(parseDate(data[4]));
        transaction.setMarketValue(Double.parseDouble(data[5]));
        transaction.setPriorityFlag(data[6].equals("Y")?true:false);
        return transaction;
    }
}
