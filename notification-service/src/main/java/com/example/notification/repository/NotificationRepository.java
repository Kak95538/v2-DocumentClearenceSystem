package com.example.notification.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notification.entity.NotificationLog;

public interface NotificationRepository extends JpaRepository<NotificationLog, Long> {
}