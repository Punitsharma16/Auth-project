package com.example.auth_app_backend.DiningService.DiningResourceController;

import com.example.auth_app_backend.DiningService.dto.DiningResourceDto;
import com.example.auth_app_backend.DiningService.entity.ResourceType;

import java.util.List;

public interface DiningResourceService {

    public DiningResourceDto create(DiningResourceDto request);

    List<DiningResourceDto> getResources(ResourceType resourceType);
}
