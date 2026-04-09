package com.example.document.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.document.dto.DocumentRequestDTO;
import com.example.document.entity.DocumentRequest;
import com.example.document.repository.DocumentRepository;
import com.example.document.service.DocumentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository repo;

    @Override
    public DocumentRequest createRequest(DocumentRequestDTO dto, String email) {

        DocumentRequest doc = new DocumentRequest();
        doc.setStudentEmail(email);
        doc.setDocumentType(dto.getDocumentType());
        doc.setDescription(dto.getDescription());
        doc.setStatus("PENDING");
        doc.setCreatedAt(LocalDateTime.now());

        return repo.save(doc);
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

        return repo.save(doc);
    }
}