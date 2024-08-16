package com.example.microservices.userservice.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseErrorResponseDto<T> {
    private String status;
    private String message;
    private String timestamp;
    private String requestId;
    private T error;

    // Constructors
    public BaseErrorResponseDto() {
        this.timestamp = LocalDateTime.now().toString();
        this.requestId = UUID.randomUUID().toString();
    }

    public BaseErrorResponseDto(String status, String message, T error) {
        this();
        this.status = status;
        this.message = message;
        this.error = error;
    }
}
