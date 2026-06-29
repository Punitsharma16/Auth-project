package com.example.auth_app_backend.DiningService.DiningResourceController;

import com.example.auth_app_backend.DiningService.entity.ResourceType;
import org.springframework.stereotype.Component;

@Component
public class DisplayIdGenerator {
    public String generate(
            ResourceType type,
            long sequence) {

        String prefix =
                type == ResourceType.TABLE
                        ? "TBL"
                        : "RM";

        return prefix +
                String.format("%03d", sequence);
    }
}
