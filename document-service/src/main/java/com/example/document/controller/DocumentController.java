package com.example.document.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.document.dto.DocumentRequestDTO;
import com.example.document.entity.DocumentRequest;
//import com.example.document.jwt.JwtUtil;
import com.example.document.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService service;
    
//    @Autowired
//    private JwtUtil jwtUtil;

    @PostMapping("/request")
    public DocumentRequest createRequest(@RequestBody DocumentRequestDTO dto,
    		@RequestHeader("X-User-Email") String email) {

//        String token = authHeader.substring(7);
//        String email = jwtUtil.extractEmail(token);

        return service.createRequest(dto, email);
    }

    @GetMapping("/my")
    public List<DocumentRequest> myRequests(@RequestHeader("X-User-Email") String email) {
        return service.getMyRequests(email);
    }

    @GetMapping("/all")
    public List<DocumentRequest> allRequests(
            @RequestHeader("X-User-Role") String role) {

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied");
        }

        return service.getAllRequests();
    }

   
    
    @PutMapping("/approve/{id}")
    public DocumentRequest approve(@PathVariable Long id) {
        return service.approveDocument(id);
    }
    @PutMapping("/reject/{id}")
    public DocumentRequest reject(@PathVariable Long id) {
        return service.rejectDocument(id);
    }
    
    @PutMapping("/status/{id}")
    public DocumentRequest updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestHeader("X-User-Role") String role) {

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied");
        }

        return service.updateStatus(id, status);
    }
}
