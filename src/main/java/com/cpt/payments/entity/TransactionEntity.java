package com.cpt.payments.entity;

import lombok.Data;

@Data
public class TransactionEntity { 

	private int id;
    private int userId;
    
    private int paymentMethodId;
    private int providerId;
    private int paymentTypeId;
    private int txnStatusId;
    
    private double amount;
    private String currency;
    private String merchantTransactionReference;
    private String txnReference;
    private String providerReference;
    private String providerCode;
    private String providerMessage;
    private int retryCount;
    
}
