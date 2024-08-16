package com.example.microservices.userservice.dtos;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
