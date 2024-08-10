package com.cpt.payments.service.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cpt.payments.constant.TransactionStatusEnum;
import com.cpt.payments.service.impl.handler.CreatedStatusHandler;
import com.cpt.payments.service.interfaces.PaymentStatusHandler;

@Component
public class PaymentStatusFactory {
	
	@Autowired
	private ApplicationContext ctx;
	
	public PaymentStatusHandler getStatusHandler(TransactionStatusEnum statusEnum) {
		
		switch(statusEnum) {
		case CREATED:
			System.out.println("Creating CreatedStatusHandler for "
					+ "status:" + statusEnum);
			
			CreatedStatusHandler createdStatusHandler =  ctx.getBean(CreatedStatusHandler.class);
			System.out.println("Got bean from APplcationContext ||"
					+ "createdStatusHandler:" + createdStatusHandler);
			
			return createdStatusHandler;
		
		case INITIATED:
			return null;
		
		default:
			System.out.println("Unable to find Handler");
			return null;
		}
	}
}
