package com.example.document.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.document.dto.DocumentRequestDTO;
import com.example.document.entity.DocumentRequest;
import com.example.document.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @PostMapping("/request")
    public DocumentRequest createRequest(@RequestBody DocumentRequestDTO dto,
                                         @RequestHeader("X-User-Email") String email) {
        return service.createRequest(dto, email);
    }

    @GetMapping("/my")
    public List<DocumentRequest> myRequests(@RequestHeader("X-User-Email") String email) {
        return service.getMyRequests(email);
    }

    @GetMapping("/all")
    public List<DocumentRequest> allRequests() {
        return service.getAllRequests();
    }

    @PutMapping("/status/{id}")
    public DocumentRequest updateStatus(@PathVariable Long id,
                                        @RequestParam String status) {
        return service.updateStatus(id, status);
    }
    
    @PutMapping("/approve/{id}")
    public DocumentRequest approve(@PathVariable Long id) {
        return service.approveDocument(id);
    }
    @PutMapping("/reject/{id}")
    public DocumentRequest reject(@PathVariable Long id) {
        return service.rejectDocument(id);
    }
}
