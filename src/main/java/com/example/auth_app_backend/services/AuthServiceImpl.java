package com.example.auth_app_backend.services;

import com.example.auth_app_backend.dtos.UserDto;
import com.example.auth_app_backend.entities.User;
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
        return userService.createUser(userDto);


    }
}
