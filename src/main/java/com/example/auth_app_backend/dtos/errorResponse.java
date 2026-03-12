package com.example.auth_app_backend.dtos;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;

public record errorResponse(String message , HttpStatus status, int code) {


}
