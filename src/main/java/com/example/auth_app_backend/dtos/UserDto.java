package com.example.auth_app_backend.dtos;

import com.example.auth_app_backend.entities.Provider;
import com.example.auth_app_backend.entities.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String email;
    private String password;
    private String name;
    private String image;
    private boolean isEnable=true;
    private boolean isAdmin;
    private String phone;
    private Instant createAt=Instant.now();
    private Instant updateAt=Instant.now();
    private Provider provider;
    private Set<Roledto> roles= new HashSet<>();
}
