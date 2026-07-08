package com.kafka.notification_service.Messaging.Consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "product-events-dlt",containerFactory = "dltKafkaListenerContainerFactory")
public class ProductEventDLTConsumer {

    @KafkaHandler
    public void consume(ConsumerRecord<String,String> record){
       log.error("""
                ==========================================================
                         DEAD LETTER EVENT RECEIVED
                ==========================================================
                
                Topic          : {}
                Partition      : {}
                Offset         : {}
                Key            : {}
                Payload        : {}
                
                ==========================================================
                """,
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.value());

        /*
         * Future Enhancements
         *
         * 1. Store failed event in database
         * 2. Send Email Notification
         * 3. Notify Ops Team
         * 4. Push to Monitoring System
         * 5. Manual Reprocessing
         */

    }
}