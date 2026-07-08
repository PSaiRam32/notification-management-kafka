package com.kafka.notification_service.service;


import com.kafka.notification_service.entity.ProcessedEvent;
import com.kafka.notification_service.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProcessedEventServiceImpl implements ProcessedEventService{

    private final ProcessedEventRepository repository;
    @Override
    public boolean isProcessed(String eventId){
        return repository.existsByEventId(eventId);
    }

    @Override
    public void markProcessed(String eventId){
        ProcessedEvent processedEvent = ProcessedEvent.builder()
                        .eventId(eventId)
                        .processedAt(Instant.now())
                        .build();
        repository.save(processedEvent);
    }
}