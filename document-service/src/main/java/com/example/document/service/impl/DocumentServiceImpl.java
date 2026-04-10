package com.example.document.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.document.dto.DocumentRequestDTO;
import com.example.document.entity.DocumentRequest;
import com.example.document.repository.DocumentRepository;
import com.example.document.service.DocumentService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository repo;
    
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DocumentRequest createRequest(DocumentRequestDTO dto, String email) {

        DocumentRequest doc = new DocumentRequest();
        doc.setStudentEmail(email);
        doc.setDocumentType(dto.getDocumentType());
        doc.setDescription(dto.getDescription());
        doc.setStatus("PENDING");
        doc.setCreatedAt(LocalDateTime.now());

        DocumentRequest saved = repo.save(doc);

        // 📧 Email
        sendEmailNotification(email,
            "Your document request has been submitted successfully.");

        return saved;
    }

    @Override
    public List<DocumentRequest> getMyRequests(String email) {
        return repo.findByStudentEmail(email);
    }

    @Override
    public List<DocumentRequest> getAllRequests() {
        return repo.findAll();
    }

    @Override
    public DocumentRequest updateStatus(Long id, String status) {

        DocumentRequest doc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        doc.setStatus(status);
        doc.setUpdatedAt(LocalDateTime.now());

        DocumentRequest updated = repo.save(doc);

        // 📧 Email
        sendEmailNotification(doc.getStudentEmail(),
            "Your document status is updated to: " + status);

        return updated;
    }
    
    @Override
    public DocumentRequest approveDocument(Long id) {

        DocumentRequest doc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        doc.setStatus("APPROVED");
        doc.setUpdatedAt(LocalDateTime.now());

        DocumentRequest updated = repo.save(doc);

        sendEmailNotification(doc.getStudentEmail(),
            "🎉 Your document has been APPROVED.");

        return updated;
    }
    
    @Override
    public DocumentRequest rejectDocument(Long id) {

        DocumentRequest doc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        doc.setStatus("REJECTED");
        doc.setUpdatedAt(LocalDateTime.now());

        DocumentRequest updated = repo.save(doc);

        sendEmailNotification(doc.getStudentEmail(),
            "❌ Your document has been REJECTED. Please re-upload.");

        return updated;
    }
    
    @Override
    public void sendEmailNotification(String email, String message) {

        String url = "http://localhost:8084/notify";

        Map<String, String> request = new HashMap<>();
        request.put("to", email);
        request.put("subject", "Document Service Update");
        request.put("message", message);
        request.put("type", "EMAIL");

        restTemplate.postForObject(url, request, String.class);
    }
    
    
}