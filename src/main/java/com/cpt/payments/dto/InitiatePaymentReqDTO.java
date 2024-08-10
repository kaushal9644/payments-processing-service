package com.cpt.payments.dto;

import java.util.List;

import lombok.Data;

@Data
public class InitiatePaymentReqDTO {
	
	private int id;
	
	private List<LineItemDTO> lineItem;
	
	private String successUrl;
	private String cancelUrl;

}
