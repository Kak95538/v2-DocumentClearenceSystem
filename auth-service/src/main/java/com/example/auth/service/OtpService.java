package com.example.auth.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auth.entity.Otp;
import com.example.auth.repository.OtpRepository;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public String generateOtp(String mobile) {
    	//logger.info("Generating OTP for mobile: {}", mobile);
    	otpRepository.deleteAll(
    		    otpRepository.findAll().stream()
    		        .filter(o -> o.getMobile().equals(mobile))
    		        .toList()
    		);
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        Otp otpEntity = new Otp();
        otpEntity.setMobile(mobile);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otpEntity);

        return otp;
    }

    public boolean validateOtp(String mobile, String otp) {

    	Otp otpEntity = otpRepository
    	        .findTopByMobileOrderByExpiryTimeDesc(mobile)
    	        .orElseThrow(() -> new RuntimeException("OTP not found"));
    	
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        return otpEntity.getOtp().equals(otp);
    }
}
