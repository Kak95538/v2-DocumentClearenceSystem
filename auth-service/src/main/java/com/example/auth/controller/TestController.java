package com.example.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/admin")
    public String adminApi() {
        return "Welcome Admin 🔥";
    }

    @GetMapping("/student")
    public String studentApi() {
        return "Welcome Student 🎓";
    }
}