package com.kafka.notification_service.Messaging.event;

import lombok.*;
import java.time.Instant;

//Responsibility - Send metadata

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEvent<T> {

    private String eventId;
    private String eventVersion;
    private EventType eventType;
    private Instant eventTime;
    private String source;
    private String correlationId;
    private T payload;

}