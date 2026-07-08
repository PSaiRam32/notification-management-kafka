package com.kafka.notification_service.Messaging.event;

import com.kafka.notification_service.Exception.NonRetryableException;
import org.springframework.stereotype.Component;

@Component
public class ProductEventValidator{

    public void validate(BaseEvent<ProductPayload> event){
        if(event == null){
            throw new NonRetryableException("Event cannot be null.");
        }
        if(event.getPayload() == null){
            throw new NonRetryableException("Payload cannot be null.");
        }
        ProductPayload payload=event.getPayload();
        if(payload.getId() == null){
            throw new NonRetryableException("Product Id is mandatory.");
        }
        if(payload.getSku() == null || payload.getSku().isBlank()){
            throw new NonRetryableException("SKU is mandatory.");
        }
        if(payload.getProductName() == null || payload.getProductName().isBlank()){
            throw new NonRetryableException("Product Name is mandatory.");
        }
        if(payload.getPrice() == null){
            throw new NonRetryableException("Price is mandatory.");
        }
        if(payload.getPrice().signum() < 0){
            throw new NonRetryableException("Price cannot be negative.");
        }
        if(payload.getQuantity() == null){
            throw new NonRetryableException("Quantity is mandatory.");
        }
        if(payload.getQuantity() < 0){
            throw new NonRetryableException("Quantity cannot be negative.");
        }
        if(payload.getStatus() == null){
            throw new NonRetryableException("Product Status is mandatory.");
        }
    }
}