package com.example.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.auth.dto.Payment;

@RestController
@RequestMapping("/auth/payment")
public class PaymentProxyController {

    
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/pay")
    public Payment makePayment(
            @RequestHeader("Authorization") String token,
            @RequestBody Payment payment) {

        String url = "http://localhost:8082/payment/pay";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Payment> entity = new HttpEntity<>(payment, headers);

        ResponseEntity<Payment> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Payment.class
        );

        return response.getBody();
    }
}