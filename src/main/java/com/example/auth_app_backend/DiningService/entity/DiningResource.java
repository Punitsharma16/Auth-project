package com.example.auth_app_backend.DiningService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "dining_resources")
public class DiningResource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;

    @Column(name = "resource_number", nullable = false, length = 50)
    private String number;

    @Column(name = "display_id", unique = true, nullable = false, length = 50)
    private String displayId;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "room_type", length = 50)
    private String roomType;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
    @Column
    private String qrCodeUrl;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        if (displayId == null) {
            displayId = resourceType == ResourceType.TABLE
                ? "TBL" + String.format("%03d", (int) (Math.random() * 999))
                : "RM" + String.format("%03d", (int) (Math.random() * 999) + 100);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
