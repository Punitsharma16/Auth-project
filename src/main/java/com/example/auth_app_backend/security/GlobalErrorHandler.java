package com.example.auth_app_backend.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {


    // ✅ Handle all unhandled exceptions (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Server Error",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handle404(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "Not Found",
                        "message", "API endpoint does not exist"
                ));
    }
}
