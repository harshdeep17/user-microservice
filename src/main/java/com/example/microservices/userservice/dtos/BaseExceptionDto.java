package com.example.microservices.userservice.dtos;

import lombok.Data;

@Data
public class BaseExceptionDto {
    private String code;
    private String description;
}