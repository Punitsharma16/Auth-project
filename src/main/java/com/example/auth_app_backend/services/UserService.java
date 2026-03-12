package com.example.auth_app_backend.services;

import com.example.auth_app_backend.dtos.UserDto;

public interface UserService {
    //create user
    UserDto createUser(UserDto userDto);
    //get User by email id
    UserDto getUserByEmail(String email);
    //update user
    UserDto updateUser(UserDto userDto,String userId);
    //delete user
    void deleteUser(String userId);
    //get All Users
    Iterable<UserDto>getAllUser();
    //get user By id
    UserDto getUserById(String userId);

}
