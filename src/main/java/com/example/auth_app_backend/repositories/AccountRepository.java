package com.example.auth_app_backend.repositories;

import com.example.auth_app_backend.dtos.AccountMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<AccountMaster,String> {
}
