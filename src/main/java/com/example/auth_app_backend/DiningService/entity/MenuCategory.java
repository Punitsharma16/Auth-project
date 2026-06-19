package com.example.auth_app_backend.DiningService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menu_categories")
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 100)
    private String icon;

    @Column(name = "item_count")
    private int itemCount;
}
