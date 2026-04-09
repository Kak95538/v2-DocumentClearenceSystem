package com.example.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dto.LoginRequest;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.JwtUtil;
import com.example.auth.service.OtpService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private OtpService otpService;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

//	@GetMapping("/test")
//		public String test() {
//			return "test success";
//		}
	
	@GetMapping("/admin/test")
	public String adminApi() {
	    return "Admin access granted";
	}
	
	
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest request) {

	    logger.info("Login attempt for email: {}", request.getEmail());

	    User user = userRepository.findByEmail(request.getEmail())
	        .orElseThrow(() -> {
	            logger.error("User not found: {}", request.getEmail());
	            return new RuntimeException("User not found");
	        });

	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        logger.warn("Invalid password attempt for: {}", request.getEmail());
	        throw new RuntimeException("Invalid password");
	    }

	    String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

	    logger.info("Login successful for: {}", request.getEmail());

	    return token;
	}
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {

	    logger.info("Registering user: {}", user.getEmail());

	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    user.setRole("ROLE_USER");
	    userRepository.save(user);

	    return "User registered successfully";
	}
	
	@PostMapping("/send-otp")
	public String sendOtp(@RequestParam String mobile) {

	    String otp = otpService.generateOtp(mobile);

	    logger.info("OTP sent to {} is {}", mobile, otp); // simulate SMS

	    return "OTP sent successfully";
	}
	
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam String mobile,
	                        @RequestParam String otp) {

	    boolean isValid = otpService.validateOtp(mobile, otp);

	    if (!isValid) {
	        throw new RuntimeException("Invalid OTP");
	    }

	    String token = jwtUtil.generateToken(mobile,"USER");

	    return token;
	}

}
