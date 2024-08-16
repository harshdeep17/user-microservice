package com.example.microservices.userservice.exceptions;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException{
    private String code;
    private String message;

    public UserNotFoundException(String code,String message) {
        super(message);
        this.code= code;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
