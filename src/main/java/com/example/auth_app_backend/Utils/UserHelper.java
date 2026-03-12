package com.example.auth_app_backend.Utils;

import java.util.UUID;

public class UserHelper {
    public static UUID parseUUID(String userId){
        return UUID.fromString(userId);
    }
}
