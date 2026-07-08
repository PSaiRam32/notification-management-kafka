package com.kafka.notification_service.Messaging.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.notification_service.Messaging.event.BaseEvent;
import com.kafka.notification_service.Messaging.event.ProductPayload;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ProductEventDeserializer implements Deserializer<BaseEvent<ProductPayload>>{

    private final ObjectMapper objectMapper;
    private final JavaType javaType;

    public ProductEventDeserializer(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        javaType = objectMapper.getTypeFactory().constructParametricType(BaseEvent.class,ProductPayload.class);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey){
        // No configuration required
    }

    @Override
    public BaseEvent<ProductPayload> deserialize(String topic, byte[] data){
        if (data == null){
            return null;
        }
        try{
            return objectMapper.readValue(data, javaType);
        }
        catch (Exception ex){
            throw new RuntimeException("Failed to deserialize Product Event",ex);
        }
    }
    @Override
    public void close() {
        // Nothing to close
    }
}