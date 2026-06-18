package com.example.auth_app_backend.DiningService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dining_customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 200)
    private String name;

    @Column(unique = true, nullable = false, length = 20)
    private String mobile;

    @Column(length = 300)
    private String email;

    @Column(name = "total_orders")
    private int totalOrders;

    @Column(name = "loyalty_points")
    private int loyaltyPoints;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
