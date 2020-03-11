package com.calc.model;

import lombok.Data;
import java.util.Comparator;
import java.util.Date;
import com.calc.enumeration.TransactionType;

/**
 * @author anujkumar
 *
 * Model class is to represent transactions data
 *
 */
@Data
public class TransactionsFeed implements Comparator<TransactionsFeed> {

    /**
     * externalTransactionId
     * */
    private String extTransactionId;
    /**
     * clientId
     * */
    private String clientId;
    /**
     * securityId
     * */
    private String securityId;
    /**
     * transactionType
     * */
    private TransactionType transactionType;
    /**
     * transactionDate
     * */
    private Date transactionDate;
    /**
     * marketValue
     * */
    private double marketValue;
    /**
     * priorityFlag
     * */
    private Boolean priorityFlag;
    /**
     * processingFee
     * */
    private double processingFee;

    @Override
    public int compare(TransactionsFeed obj1, TransactionsFeed obj2) {
        int result = obj1.getClientId().compareTo(obj2.getClientId());
        if (result == 0) {
            int result1 = obj1.getTransactionType().compareTo(obj2.getTransactionType());
            if (result1 == 0) {
                int result2 = obj1.getTransactionDate().compareTo(obj2.getTransactionDate());
                if (result2 == 0) {
                    return obj1.getPriorityFlag().compareTo(obj2.getPriorityFlag());
                } else {
                    return result2;
                }
            } else {
                return result1;
            }
        } else {
            return result;
        }
    }
}
