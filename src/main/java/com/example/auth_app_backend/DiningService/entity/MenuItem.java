package com.example.auth_app_backend.DiningService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "item_code", unique = true, nullable = false, length = 50)
    private String itemCode;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(length = 500)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory category;

    @Column(name = "is_veg")
    private boolean isVeg;

    @Column(name = "is_available")
    private boolean isAvailable;

    private double rating;

    @Column(name = "preparation_time")
    private int preparationTime;

    @PrePersist
    protected void onCreate() {
        if (itemCode == null) {
            itemCode = "ITM" + String.format("%03d", (int) (Math.random() * 9999));
        }
    }
}
