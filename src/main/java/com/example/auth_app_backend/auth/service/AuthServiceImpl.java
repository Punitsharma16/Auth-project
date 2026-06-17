package com.example.auth_app_backend.auth.service;

import com.example.auth_app_backend.user.dto.UserDto;
import com.example.auth_app_backend.user.entity.User;
import com.example.auth_app_backend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDto registerUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setParentId("0");
        return userService.createUser(userDto);
    }
}
