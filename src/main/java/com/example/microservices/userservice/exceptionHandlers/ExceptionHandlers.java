package com.example.microservices.userservice.exceptionHandlers;

import com.example.microservices.userservice.constants.ResponseMessages;
import com.example.microservices.userservice.dtos.BaseErrorResponseDto;
import com.example.microservices.userservice.dtos.BaseExceptionDto;
import com.example.microservices.userservice.exceptions.InvalidCredentialsException;
import com.example.microservices.userservice.exceptions.TokenNotFoundException;
import com.example.microservices.userservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<BaseErrorResponseDto<BaseExceptionDto>>
    handleUserNotFoundException(UserNotFoundException userNotFoundException){
        BaseExceptionDto baseExceptionDto = new BaseExceptionDto();
        baseExceptionDto.setCode(userNotFoundException.getCode());
        baseExceptionDto.setDescription(userNotFoundException.getMessage());
        BaseErrorResponseDto<BaseExceptionDto> response =
                new BaseErrorResponseDto<>(ResponseMessages.FAILURE_STATUS,
                        ResponseMessages.USER_NOT_FOUND,baseExceptionDto);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<BaseErrorResponseDto<BaseExceptionDto>>
    handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException){
        BaseExceptionDto baseExceptionDto = new BaseExceptionDto();
        baseExceptionDto.setCode(invalidCredentialsException.getCode());
        baseExceptionDto.setDescription(invalidCredentialsException.getMessage());
        BaseErrorResponseDto<BaseExceptionDto> response =
                new BaseErrorResponseDto<>(ResponseMessages.FAILURE_STATUS,
                        ResponseMessages.INVALID_CREDENTIALS,baseExceptionDto);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(TokenNotFoundException.class)
    ResponseEntity<BaseErrorResponseDto<BaseExceptionDto>>
    handleTokenNotFoundException(TokenNotFoundException tokenNotFoundException){
        BaseExceptionDto baseExceptionDto = new BaseExceptionDto();
        baseExceptionDto.setCode(tokenNotFoundException.getCode());
        baseExceptionDto.setDescription(tokenNotFoundException.getMessage());
        BaseErrorResponseDto<BaseExceptionDto> response =
                new BaseErrorResponseDto<>(ResponseMessages.FAILURE_STATUS,
                        ResponseMessages.INVALID_TOKEN,baseExceptionDto);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
