package com.example.auth_app_backend.dtos;

import com.example.auth_app_backend.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String accountId;

    private Long accountNumber;

    private String fullname;

    private String createBy;
    private String updateBy;

    private String email;
    private String phone;

    private String logo;
    private String company;

    private boolean active;
    private boolean hasTwoStepAuthencation;

    private Instant createAt;
    private Instant updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // SQL user id reference
}
