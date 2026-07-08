package com.kafka.notification_service.Messaging.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.kafka.notification_service.Exception.NonRetryableException;
import com.kafka.notification_service.Exception.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaErrorHandlerConfig{


    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate<Object,Object> kafkaTemplate) {
        return new DeadLetterPublishingRecoverer(kafkaTemplate,(record, ex) -> {
                    String destinationTopic = record.topic() + "-dlt";
                    log.error("""
                            Publishing failed message
                            Source Topic      : {}
                            Destination Topic : {}
                            Partition         : {}
                            Offset            : {}
                            """,
                            record.topic(),destinationTopic,record.partition(),record.offset());
                    return new TopicPartition(destinationTopic, record.partition());
                });
    }

    @Bean
    public DefaultErrorHandler kafkaErrorHandler(DeadLetterPublishingRecoverer recoverer){
        FixedBackOff fixedBackOff=new FixedBackOff(2000L, 3L);
        DefaultErrorHandler errorHandler=new DefaultErrorHandler(recoverer,fixedBackOff);
        errorHandler.addRetryableExceptions(RetryableException.class,org.springframework.dao.DataAccessException.class);
        errorHandler.addNotRetryableExceptions(NonRetryableException.class,
                JsonParseException.class,MismatchedInputException.class,
                InvalidFormatException.class,DeserializationException.class);
        return errorHandler;
    }

}