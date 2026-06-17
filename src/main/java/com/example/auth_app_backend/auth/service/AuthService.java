package com.example.auth_app_backend.auth.service;

import com.example.auth_app_backend.user.dto.UserDto;

public interface AuthService {
    UserDto registerUser(UserDto userDto);
}
