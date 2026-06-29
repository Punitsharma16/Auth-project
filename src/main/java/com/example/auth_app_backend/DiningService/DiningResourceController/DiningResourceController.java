package com.example.auth_app_backend.DiningService.DiningResourceController;

import com.example.auth_app_backend.DiningService.dto.DiningResourceDto;
import com.example.auth_app_backend.DiningService.entity.ResourceType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")

public class DiningResourceController {
    private final DiningResourceService service;

    public DiningResourceController(DiningResourceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DiningResourceDto> create(@RequestBody DiningResourceDto request) {
        return ResponseEntity.ok(
                service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<DiningResourceDto>> getResources(@RequestParam ResourceType resourceType) {

        return ResponseEntity.ok(
                service.getResources(resourceType));
    }
}
