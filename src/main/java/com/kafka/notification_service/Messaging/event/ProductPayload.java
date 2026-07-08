package com.kafka.notification_service.Messaging.event;

import com.kafka.notification_service.entity.ProductStatus;
import lombok.*;
import java.math.BigDecimal;

//Responsibility - Send Only business data.(Data Contract)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPayload {
    private Long id;
    private String productName;
    private String description;
    private String category;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private ProductStatus status;
}
