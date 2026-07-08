package com.kafka.notification_service.service;

import com.kafka.notification_service.entity.Notification;

public interface EmailService {
    void sendEmail(Notification notification);
}