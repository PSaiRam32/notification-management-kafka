package com.kafka.notification_service.entity;

import com.kafka.notification_service.Messaging.event.EventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name="notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String eventId;
    @Column(nullable=false)
    private String correlationId;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private EventType eventType;
    @Column(nullable=false)
    private Long productId;
    @Column(nullable=false)
    private String productName;
    @Column(nullable=false)
    private String sku;
    @Column(nullable=false)
    private String recipientEmail;
    @Column(nullable=false)
    private String subject;
    @Lob
    private String body;
    @Column(nullable=false)
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private NotificationStatus status;
    @Lob
    private String failureReason;
    @CreationTimestamp
    private Instant createdAt;
    private Instant sentAt;
}