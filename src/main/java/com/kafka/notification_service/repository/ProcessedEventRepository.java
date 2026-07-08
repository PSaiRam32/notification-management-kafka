package com.kafka.notification_service.repository;

import com.kafka.notification_service.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent,Long> {
    boolean existsByEventId(String eventId);
}
