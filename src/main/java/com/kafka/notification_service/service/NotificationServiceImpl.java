package com.kafka.notification_service.service;

import com.kafka.notification_service.Exception.RetryableException;
import com.kafka.notification_service.entity.Notification;
import com.kafka.notification_service.entity.NotificationStatus;
import com.kafka.notification_service.Messaging.event.BaseEvent;
import com.kafka.notification_service.Messaging.event.EventType;
import com.kafka.notification_service.Messaging.event.ProductPayload;
import com.kafka.notification_service.entity.ProcessedEvent;
import com.kafka.notification_service.repository.NotificationRepository;
import com.kafka.notification_service.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final ProcessedEventRepository processedEventRepository;
    private final EmailService emailService;
    @Value("${spring.notification.email.recipient}")
    private String recipientEmail;

    @Override
    @Transactional
    public void processNotification(BaseEvent<ProductPayload> event){
        if(processedEventRepository.existsByEventId(event.getEventId())){
            log.warn("Duplicate Event {} ignored", event.getEventId());
            return;
        }
        log.info("Processing {} event with EventId={} and CorrelationId={}",
                event.getEventType(),event.getEventId(),event.getCorrelationId());
//        throw new RetryableException("Testing Retry");
        Notification notification = mapToNotification(event);
        Notification savedNotification =
                notificationRepository.save(notification);
        try {
            emailService.sendEmail(savedNotification);
            savedNotification.setStatus(NotificationStatus.SENT);
            savedNotification.setSentAt(Instant.now());
            savedNotification.setFailureReason(null);
            notificationRepository.save(savedNotification);
            processedEventRepository.save(
                    ProcessedEvent.builder()
                            .eventId(event.getEventId())
                            .processedAt(Instant.now())
                            .build());
            log.info("Notification sent successfully for EventId={}",
                    event.getEventId());
        }
        catch (Exception ex){
            savedNotification.setStatus(NotificationStatus.FAILED);
            savedNotification.setFailureReason(ex.getMessage());
            notificationRepository.save(savedNotification);
            log.error("Email sending failed for EventId={}",event.getEventId(), ex);
            throw new RetryableException("Email sending failed", ex);
        }
    }

    private Notification mapToNotification(BaseEvent<ProductPayload> event){
        ProductPayload payload = event.getPayload();
        return Notification.builder()
                .eventId(event.getEventId())
                .correlationId(event.getCorrelationId())
                .eventType(event.getEventType())
                .productId(payload.getId())
                .productName(payload.getProductName())
                .sku(payload.getSku())
                .message(generateNotificationMessage(event.getEventType(), payload))
                .status(NotificationStatus.PENDING)
                .recipientEmail(recipientEmail)
                .subject(generateSubject(event.getEventType()))
                .body(generateEmailBody(event))
                .build();
    }

    private String generateNotificationMessage(EventType eventType,ProductPayload payload){
        return switch (eventType){
            case PRODUCT_CREATED -> String.format(
                            "Product '%s' (SKU: %s) has been created successfully.",
                            payload.getProductName(),payload.getSku());
            case PRODUCT_UPDATED ->
                    String.format("Product '%s' (SKU: %s) has been updated successfully.",
                            payload.getProductName(),payload.getSku());
            case PRODUCT_DELETED ->
                    String.format("Product '%s' (SKU: %s) has been deleted successfully.",
                            payload.getProductName(),payload.getSku());
        };
    }
    private String generateEmailBody(BaseEvent<ProductPayload> event){
        ProductPayload payload = event.getPayload();
        return """
        <html>

        <body style="font-family:Arial,sans-serif">

        <h2 style="color:#0d6efd;">
            Product Event Notification
        </h2>

        <p>Hello,</p>

        <p>
            A product lifecycle event has been processed successfully.
        </p>

        <table border="1"
               cellpadding="8"
               cellspacing="0"
               style="border-collapse:collapse;">

            <tr>
                <th align="left">Event Type</th>
                <td>%s</td>
            </tr>

            <tr>
                <th align="left">Product ID</th>
                <td>%d</td>
            </tr>

            <tr>
                <th align="left">Product Name</th>
                <td>%s</td>
            </tr>

            <tr>
                <th align="left">SKU</th>
                <td>%s</td>
            </tr>

            <tr>
                <th align="left">Event ID</th>
                <td>%s</td>
            </tr>

            <tr>
                <th align="left">Correlation ID</th>
                <td>%s</td>
            </tr>

        </table>

        <br/>

        <p>
            This is an automated notification generated by the
            Inventory Management Platform.
        </p>

        <br/>

        Regards,<br/>
        Inventory Management Platform

        </body>

        </html>
        """
                .formatted(
                        event.getEventType(),
                        payload.getId(),
                        payload.getProductName(),
                        payload.getSku(),
                        event.getEventId(),
                        event.getCorrelationId());
    }
    private String generateSubject(EventType eventType) {
        return switch (eventType) {
            case PRODUCT_CREATED -> "Product Created Successfully";
            case PRODUCT_UPDATED -> "Product Updated Successfully";
            case PRODUCT_DELETED -> "Product Deleted Successfully";
        };
    }
}