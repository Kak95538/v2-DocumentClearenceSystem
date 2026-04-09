package com.example.document.service;


import java.util.List;

import com.example.document.dto.DocumentRequestDTO;
import com.example.document.entity.DocumentRequest;

public interface DocumentService {

    DocumentRequest createRequest(DocumentRequestDTO dto, String email);

    List<DocumentRequest> getMyRequests(String email);

    List<DocumentRequest> getAllRequests();

    DocumentRequest updateStatus(Long id, String status);
}