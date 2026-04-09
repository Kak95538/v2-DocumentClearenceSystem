package com.example.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payment.entity.Payment;
import com.example.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment makePayment(Payment payment) {

//        payment.setStatus("SUCCESS"); // simulate payment success
    	
    	if(payment.getAmount() > 0){
    	    payment.setStatus("SUCCESS");
    	} else {
    	    payment.setStatus("FAILED");
    	}
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}