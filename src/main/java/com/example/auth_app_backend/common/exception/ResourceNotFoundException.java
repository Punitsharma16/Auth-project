package com.example.auth_app_backend.common.exception;

public class ResourceNotFoundException extends  RuntimeException{
    public  ResourceNotFoundException(String message){
        super(message);
    }
    public ResourceNotFoundException(){
        super("Resource not found !!");
    }
}
