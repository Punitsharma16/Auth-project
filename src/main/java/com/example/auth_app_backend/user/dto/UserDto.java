package com.example.auth_app_backend.user.dto;

import com.example.auth_app_backend.auth.entity.Provider;
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
    private UUID id;
    private String email;
    private String password;
    private String name;
    private String image;
    private boolean isEnable=true;
    private boolean isAdmin;
    private String phone;
    private String company;
    private String parentId;
    private Instant createAt=Instant.now();
    private Instant updateAt=Instant.now();
    private Provider provider;
    private Set<Roledto> roles= new HashSet<>();
}
