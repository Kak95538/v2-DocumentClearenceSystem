package com.example.document.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.document.entity.DocumentRequest;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentRequest, Long> {

    List<DocumentRequest> findByStudentEmail(String email);
}