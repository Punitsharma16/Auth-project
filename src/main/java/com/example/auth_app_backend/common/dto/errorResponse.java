package com.example.auth_app_backend.common.dto;

import org.springframework.http.HttpStatus;

public record errorResponse(String message , HttpStatus status, int code) {


}
