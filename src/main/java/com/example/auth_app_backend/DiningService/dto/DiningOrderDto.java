package com.example.auth_app_backend.DiningService.dto;

import com.example.auth_app_backend.DiningService.entity.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DiningOrderDto {

    private UUID id;
    private String orderCode;
    private ResourceType resourceType;
    private String resourceId;
    private String customerName;
    private String customerMobile;
    private List<OrderItemDto> items;
    private double subtotal;
    private double tax;
    private double grandTotal;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private int estimatedTime;
    private String notes;
    private Instant scheduledTime;
    private Instant createdAt;
    private Instant updatedAt;
}
