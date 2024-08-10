package com.cpt.payments.service.impl.provider.handler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpt.payments.dto.InitiatePaymentResDTO;
import com.cpt.payments.dto.InitiatePaymentReqDTO;
import com.cpt.payments.dto.TransactionDTO;
import com.cpt.payments.service.http.HttpRequest;
import com.cpt.payments.service.http.HttpServiceEngine;
import com.cpt.payments.service.interfaces.ProviderHandler;
import com.cpt.payments.stripeprovider.CreatePaymentReq;
import com.cpt.payments.stripeprovider.CreatePaymentRes;
import com.google.gson.Gson;

@Service
public class StripeProviderHandler implements ProviderHandler {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private HttpServiceEngine httpServiceEngine;
	
	@Autowired
	private Gson gson;
	
	@Value("${stripeprovider.payments.createpayment}")
	private String stripeCreatePaymentUrl;
	
	@Override
	public InitiatePaymentResDTO processPayment(
			TransactionDTO txn, InitiatePaymentReqDTO req) {
		// TODO Auto-generated method stub
		
		System.out.println("StripeProviderHandler.processPayment()"
				+ "|txn:" + txn
				+ "|req:" + req);
		
		//TODO, finalize the request to pass to stripeProvider
		
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        CreatePaymentReq paymentRequest = modelMapper.map(req, CreatePaymentReq.class);
        paymentRequest.setTxnRef(txn.getTxnReference());
        
        
        System.out.println("Request for StripeProvider"
        		+ "|paymentRequest:" + paymentRequest);
        
        //Prepare HttpRequest for passing to HttpServiceEngine
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrl(stripeCreatePaymentUrl);
        httpRequest.setMethod(HttpMethod.POST);
        httpRequest.setRequest(paymentRequest);
        httpRequest.setHttpHeaders(httpHeaders);
        
        ResponseEntity<String> response = httpServiceEngine.makeHttpRequest(
        		httpRequest);

        System.out.println("RESPONSE from http service ||response:" + response.getBody());
        

        if (response.getStatusCode().is2xxSuccessful()) {
        	CreatePaymentRes responseAsObj = gson.fromJson(response.getBody(), CreatePaymentRes.class);
        	
        	InitiatePaymentResDTO resDto = modelMapper.map(responseAsObj, InitiatePaymentResDTO.class);
        	resDto.setTxnReference(txn.getTxnReference());
        	
        	System.out.println("resDto:" + resDto);
        	
            return resDto;
        } else {
            return new InitiatePaymentResDTO();
        }
	}

}
