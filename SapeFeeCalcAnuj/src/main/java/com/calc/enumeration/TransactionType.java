package com.calc.enumeration;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 *
 * @author anujkumar
 *
 * Enums to maintain all possible transaction types
 *
 * */
public enum TransactionType {

    BUY("BUY"), SELL("SELL"), DEPOSIT("DEPOSIT"), WITHDRAW("WITHDRAW");

    /**
     * transactionName
     * */
    private String transactionName;

    /**
     * Constructor for TransactionType enum
     *
     * @param transactionName
     */
    TransactionType(String transactionName){
        this.transactionName=transactionName;
    }

    @Getter
    public String getTransactionName(){
        return transactionName;
    }
}
