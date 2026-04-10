package com.example.notification.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.notification.entity.NotificationRequest;
import com.example.notification.service.NotificationService;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public String sendNotification(@RequestBody NotificationRequest request) {
        return notificationService.processNotification(request);
    }
}
