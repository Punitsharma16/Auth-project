package com.example.auth_app_backend.credit.repository;

import com.example.auth_app_backend.credit.entity.UsageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageLogRepo extends JpaRepository<UsageLog, Long> {
}
