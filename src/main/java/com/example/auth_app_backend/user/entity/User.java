package com.example.auth_app_backend.user.entity;

import com.example.auth_app_backend.auth.entity.Provider;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;
    @Column(name="user_email",unique = true,length = 300)
    private String email;
    private String password;
    @Column(name = "user_name",length = 500)
    private String name;
    private boolean isEnable;
    private boolean isAdmin;
    private String company;
    private String parentId;
    private Instant createAt;
    private Instant updateAt=Instant.now();
    private String image;
    @Column(name = "phone", length = 14, unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    @PrePersist
    protected void onCreate(){
        Instant now=Instant.now();
        if(updateAt==null){
            updateAt=now;
        }
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .toList();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return  true;
    }

    @Override
    public boolean isAccountNonLocked() {
         return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
         return  true;
    }

    @Override
    public boolean isEnabled() {
        return  this.isEnable;
    }

}
