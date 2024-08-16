package com.example.microservices.userservice.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class BaseSuccessResponseDto <T>{
    private String status;
    private String message;
    private String timestamp;
    private String requestId;
    private T data;

    // Constructors
    public BaseSuccessResponseDto() {
        this.timestamp = LocalDateTime.now().toString();
        this.requestId = UUID.randomUUID().toString();
    }

    public BaseSuccessResponseDto(String status, String message, T data) {
        this();
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
