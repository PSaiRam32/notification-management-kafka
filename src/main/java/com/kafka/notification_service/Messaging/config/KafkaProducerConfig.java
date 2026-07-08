package com.kafka.notification_service.Messaging.config;

import com.kafka.notification_service.Messaging.event.BaseEvent;
import com.kafka.notification_service.Messaging.event.ProductPayload;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig{

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String,Object> producerConfigs(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JacksonJsonSerializer.class);
        configs.put(JacksonJsonSerializer.ADD_TYPE_INFO_HEADERS,false);
        return configs;
    }

    @Bean
    public ProducerFactory<Object,Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Object,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

}