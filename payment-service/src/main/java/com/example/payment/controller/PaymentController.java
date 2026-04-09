package com.example.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.entity.Payment;
import com.example.payment.repository.PaymentRepository;
import com.example.payment.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    
    @PostMapping("/pay")
    public Payment pay(@RequestBody Payment payment) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();   // 🔥 comes from JWT

        payment.setEmail(email); // attach user
    	return paymentService.makePayment(payment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Payment> getAll() {
        return paymentService.getAllPayments();
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public List<Payment> getMyPayments() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return paymentRepository.findByEmail(email);
    }
}