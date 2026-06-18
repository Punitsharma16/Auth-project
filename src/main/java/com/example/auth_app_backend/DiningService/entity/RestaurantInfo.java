package com.example.auth_app_backend.DiningService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "restaurant_info")
public class RestaurantInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String logo;

    @Column(name = "cover_image", length = 500)
    private String coverImage;

    private double rating;

    @Column(length = 500)
    private String address;

    @Column(name = "open_time", length = 20)
    private String openTime;

    @Column(name = "close_time", length = 20)
    private String closeTime;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "upi_qr_image", length = 500)
    private String upiQrImage;
}
