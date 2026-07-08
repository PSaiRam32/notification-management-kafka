package com.kafka.notification_service.Exception;

public class RetryableException  extends RuntimeException{
    //Used if any issues in Business Logic
    public RetryableException(String message){
        super(message);
    }
    //Used if any issues at low level - network
    public RetryableException(String message, Throwable cause){
        super(message,cause);
    }
}
