package com.example.microservices.userservice.controllers;

import com.example.microservices.userservice.constants.ResponseMessages;
import com.example.microservices.userservice.dtos.*;
import com.example.microservices.userservice.models.User;
import com.example.microservices.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<BaseSuccessResponseDto<UserDto>> signup(@RequestBody SignupRequestDto signupRequestDto){
        User user = userService.signup(signupRequestDto.getName(),signupRequestDto.getEmail(),signupRequestDto.getPassword());
        UserDto userDto = UserDto.from(user);
        BaseSuccessResponseDto<UserDto> response =
                new BaseSuccessResponseDto<>(ResponseMessages.SUCCESS_STATUS,
                        ResponseMessages.USER_CREATED_SUCCESSFULLY, userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<BaseSuccessResponseDto<String>> login(@RequestBody LoginDto loginDto){
        String token = userService.login(loginDto.getEmail(),loginDto.getPassword());
        BaseSuccessResponseDto<String> response =
                new BaseSuccessResponseDto<>(ResponseMessages.SUCCESS_STATUS,
                        ResponseMessages.LOGIN_SUCCESSFUL, token);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/logout")
    public  ResponseEntity<BaseSuccessResponseDto<Void>> logout(@RequestBody LogoutDto logoutDto){
        userService.logout(logoutDto.getToken());
        BaseSuccessResponseDto<Void> response =
                new BaseSuccessResponseDto<>(ResponseMessages.SUCCESS_STATUS,
                        ResponseMessages.LOGOUT_SUCCESSFUL,null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/validate")
    public  ResponseEntity<BaseSuccessResponseDto<UserDto>> validateToken(@RequestBody ValidateTokenDto validateTokenDto){
        User user = userService.validateToken(validateTokenDto.getToken());
        UserDto userDto = UserDto.from(user);
        BaseSuccessResponseDto<UserDto> response =
                new BaseSuccessResponseDto<>(ResponseMessages.SUCCESS_STATUS,
                        ResponseMessages.TOKEN_VALIDATED_SUCCESSFULLY,userDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
