package com.example.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByMobile(String mobile);
    Optional<Otp> findTopByMobileOrderByExpiryTimeDesc(String mobile);
}
