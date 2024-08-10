package com.cpt.payments.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdditionController {

	@Value("${mytestkey}")
	private String keyData;
	
	
    @GetMapping("/add")
    public int add(@RequestParam int num1, @RequestParam int num2) {
        System.out.println("num1:" + num1 + "|num2:" + num2);
    	
        System.out.println("keyData:" + keyData);
        
        int sumResult = num1 + num2;
        System.out.println("sumResult:" + sumResult);

        return sumResult;
    }
}