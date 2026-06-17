package com.example.auth_app_backend.auth.dto;

import com.example.auth_app_backend.user.dto.UserDto;

public record TokenResponse(String accessToken, String refreshToken, long expiresIn, String tokenType, UserDto userDto) {
   public static TokenResponse of(String accessToken, String refreshToken, long expiresIn, UserDto userDto){
       return new TokenResponse(accessToken,refreshToken,expiresIn,"Bearer",userDto);
   }
}
