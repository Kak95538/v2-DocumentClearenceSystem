package com.example.notification.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.notification.entity.NotificationRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.MediaType;
import com.twilio.type.PhoneNumber;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;
    
   

    public String processNotification(NotificationRequest request) {

        if ("EMAIL".equalsIgnoreCase(request.getType())) {
            sendEmail(request);
        }else if ("SMS".equalsIgnoreCase(request.getType())) {
            sendSMS(request);
        }
        System.out.println("Sending email to: " + request.getTo());
        return "Notification sent successfully!";
    }
    
    private void sendSMS(NotificationRequest request) {

        String url = "https://www.fast2sms.com/dev/bulkV2";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Ri6l7wynvLYWXs13DJOSzaAoCUN8udHfEGmtqrIbjgQ2ZeFM95j2RLC84iItAMQYp3srvPSnglqcTZeK");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("route", "q");
        body.put("message", request.getMessage());
        body.put("language", "english");
        body.put("numbers", request.getTo()); // 9876543210

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String response = restTemplate.postForObject(url, entity, String.class);

        System.out.println("📱 Fast2SMS Response: " + response);
    }


    private void sendEmail(NotificationRequest request) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getMessage());

        mailSender.send(message);

        System.out.println("📧 Email Sent Successfully!");
    }
}