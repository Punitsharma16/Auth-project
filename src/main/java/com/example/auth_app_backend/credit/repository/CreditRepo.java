package com.example.auth_app_backend.credit.repository;

import com.example.auth_app_backend.credit.entity.UserCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditRepo extends JpaRepository<UserCredit, String> {

    Optional<UserCredit> findByUserId(String userId);
}
