package com.kafka.notification_service.service;

public interface ProcessedEventService{
    boolean isProcessed(String eventId);
    void markProcessed(String eventId);
}