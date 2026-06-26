package com.example.auth_app_backend.DiningService.DiningResourceController;

import com.example.auth_app_backend.DiningService.dto.DiningResourceDto;
import com.example.auth_app_backend.DiningService.entity.DiningResource;
import com.example.auth_app_backend.DiningService.entity.ResourceType;
import com.example.auth_app_backend.S3Config.QRCODES3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiningResourceServiceImpl implements DiningResourceService{
    @Autowired
    DiningResourceRepository diningResourceRepository;
    @Autowired
    DisplayIdGenerator displayIdGenerator;
    @Autowired
    QRCODES3Service qrCodeS3Service;
    @Value("${app.base.url}")
    private String baseUrl;
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
        try {
            String menuUrl = baseUrl + "/menu.html";
            String qrUrl = qrCodeS3Service.generateAndUploadQR(
                    saved.getDisplayId(),
                    menuUrl
            );
            saved.setQrCodeUrl(qrUrl);
            saved = diningResourceRepository.save(saved);
        } catch (Exception e) {
            // QR fail ho toh room creation fail na ho
            System.err.println("QR generation failed: " + e.getMessage());
        }
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
                         entity.getQrCodeUrl())
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
