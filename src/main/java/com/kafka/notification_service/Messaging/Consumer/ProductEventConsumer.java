package com.kafka.notification_service.Messaging.Consumer;

import com.kafka.notification_service.Messaging.event.BaseEvent;
import com.kafka.notification_service.Messaging.event.ProductEventValidator;
import com.kafka.notification_service.Messaging.event.ProductPayload;
import com.kafka.notification_service.service.NotificationService;
import com.kafka.notification_service.service.ProcessedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "product-events",groupId = "notification-service-group")
public class ProductEventConsumer {

    private final NotificationService notificationService;
    private final ProductEventValidator validator;

    @KafkaHandler
    public void handle(BaseEvent<ProductPayload> event, Acknowledgment acknowledgment){
        log.info("Received {} event with EventId={} for SKU={}",event.getEventType(),event.getEventId(),event.getPayload().getSku());
        validator.validate(event);
        notificationService.processNotification(event);
        log.info("Sending Manual Ack");
        acknowledgment.acknowledge();
        log.info("Event {} processed successfully",event.getEventId());
        log.info("Offset committed for EventId={}",event.getEventId());
    }

}