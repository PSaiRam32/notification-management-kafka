package com.kafka.notification_service.service;


import com.kafka.notification_service.Messaging.event.BaseEvent;
import com.kafka.notification_service.Messaging.event.ProductPayload;

public interface NotificationService {

    void processNotification(BaseEvent<ProductPayload> event);

}