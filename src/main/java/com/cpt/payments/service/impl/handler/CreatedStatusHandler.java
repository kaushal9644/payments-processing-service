package com.cpt.payments.service.impl.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cpt.payments.dao.interfaces.TransactionDao;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.service.interfaces.PaymentStatusHandler;

@Component
public class CreatedStatusHandler extends PaymentStatusHandler {

	@Autowired
	private TransactionDao txnDao;
	
	@Override
	public TransactionDTO processStatus(TransactionDTO payment) {
		System.out.println("CreatedStatusHandler.processStatus()|| "
				+ "payment:" + payment);
		
		TransactionDTO txnCreatedResponse = txnDao.createTransaction(payment);
		
		System.out.println("REceived below response from DAO"
				+ "|txnCreatedResponse:" + txnCreatedResponse);
		
		return txnCreatedResponse;
	}

}
