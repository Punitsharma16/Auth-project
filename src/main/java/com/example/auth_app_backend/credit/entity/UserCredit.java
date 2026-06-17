package com.example.auth_app_backend.credit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserCredit {

    @Id
    private String userId;
    private long totalCredits;
    private long usedCredits;
}
