package com.kafka.notification_service.Messaging.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic productEventsDLT() {
        return TopicBuilder.name("product-events-dlt")
                .partitions(3)
                .replicas(2)
                .build();
    }
}