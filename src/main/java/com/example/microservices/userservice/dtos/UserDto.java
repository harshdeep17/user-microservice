package com.example.microservices.userservice.dtos;

import com.example.microservices.userservice.models.Role;
import com.example.microservices.userservice.models.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto{
    private String name;
    private String email;
    private List<Role> roles;
    public static UserDto from(User user){
        if(user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
