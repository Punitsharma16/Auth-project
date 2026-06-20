package com.example.auth_app_backend.DiningService.DiningResourceController;

import com.example.auth_app_backend.DiningService.entity.DiningResource;
import com.example.auth_app_backend.DiningService.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiningResourceRepository extends JpaRepository<DiningResource,Long> {
    boolean existsByResourceTypeAndNumber(ResourceType resourceType, String number);

    List<DiningResource> findByResourceType(ResourceType resourceType);
}
