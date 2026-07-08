package com.kafka.notification_service.Messaging.config;

import com.kafka.notification_service.Messaging.event.BaseEvent;
import com.kafka.notification_service.Messaging.event.ProductPayload;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig{

    private final DefaultErrorHandler defaultErrorHandler;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String offSetReset;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String autocommit;
    @Value("${spring.kafka.consumer.properties.max.poll.records}")
    private String pollRecords;
    @Value("${spring.kafka.consumer.properties.max.poll.interval.ms}")
    private String intervalPollMs;
    @Value("${spring.kafka.consumer.properties.session.timeout.ms}")
    private String sessionMs;
    @Value("${spring.kafka.consumer.properties.heartbeat.interval.ms}")
    private String heartbeat;

    public Map<String, Object> consumerConfigs(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
//        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JacksonJsonDeserializer.class);
//        Pulls all Messages from Beginning
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,offSetReset);
        //Automatic offset commits - False (No Data Loss if Consumer crashes)
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,autocommit);
        //Maximum records returned in one poll
        configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,pollRecords);
        //Maximum allowed processing time between polls
        configs.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,intervalPollMs);
        //Time before Kafka considers the consumer dead
        configs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,sessionMs);
        //Interval between heartbeats
        configs.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,heartbeat);
        return configs;
    }

    @Bean
    public ConsumerFactory<String, BaseEvent<ProductPayload>> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),new ProductEventDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,BaseEvent<ProductPayload>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,BaseEvent<ProductPayload>> factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(defaultErrorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> dltKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> factory=new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, String> consumerFactory=new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                        new StringDeserializer(),new StringDeserializer());
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}