package com.example.auth_app_backend.DiningService.DiningResourceController;

import com.example.auth_app_backend.DiningService.dto.DiningResourceDto;
import com.example.auth_app_backend.DiningService.entity.DiningResource;
import com.example.auth_app_backend.DiningService.entity.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiningResourceServiceImpl implements DiningResourceService{
    @Autowired
    DiningResourceRepository diningResourceRepository;
    @Autowired
    DisplayIdGenerator displayIdGenerator;
    @Override
    public DiningResourceDto create(DiningResourceDto request) {
        if (diningResourceRepository.existsByResourceTypeAndNumber(
                request.getResourceType(),
                request.getNumber())) {

            throw new RuntimeException(
                    "Resource already exists");
        }

        DiningResource diningResource=DiningResource.builder()
                .resourceType(request.getResourceType())
                .number(request.getNumber())
                 .capacity(request.getCapacity())
                .isOccupied(false)
                .build();
        DiningResource saved=diningResourceRepository.save(diningResource);
        // Generate displayId using generated ID

        String displayId= displayIdGenerator.generate(saved.getResourceType(),saved.getId());
        saved.setDisplayId(displayId);
        saved = diningResourceRepository.save(saved);

        return map(saved);

    }
    private DiningResourceDto map(DiningResource entity){
        return DiningResourceDto.builder()
                .id(entity.getId())
                .displayId(entity.getDisplayId())
                .number(entity.getNumber())
                .capacity(entity.getCapacity())
                .isOccupied(entity.isOccupied())
                .resourceType(entity.getResourceType())
                .qrUrl(
                        "https://restaurant.com/menu/"
                                + entity.getDisplayId())
                .build();
    }
    @Override
    public List<DiningResourceDto> getResources(
            ResourceType resourceType) {
        List<DiningResource> resources = diningResourceRepository.findByResourceType(resourceType);
        return resources.stream()
                .map(this::map)
                .toList();
    }

}
