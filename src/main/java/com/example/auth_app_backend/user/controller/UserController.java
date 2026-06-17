package com.example.auth_app_backend.user.controller;

import com.example.auth_app_backend.user.dto.UserDto;
import com.example.auth_app_backend.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        System.out.println("userDto: " + userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Iterable<UserDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto userDto,
            @PathVariable String userId) {
        return ResponseEntity.ok(userService.updateUser(userDto, userId));
    }
    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto>getUserById(@PathVariable ("userId")String userId){
        return ResponseEntity.ok(userService.getUserById(userId));

    }

}
