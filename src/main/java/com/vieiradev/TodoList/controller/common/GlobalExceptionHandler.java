package com.vieiradev.TodoList.controller.common;

import com.vieiradev.TodoList.dto.user.UserResponseErrorDTO;
import com.vieiradev.TodoList.dto.user.UserResponseFieldDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public UserResponseErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErros =  e.getFieldErrors();
        List<UserResponseFieldDTO> listErrors = fieldErros
                .stream()
                .map(fe -> new UserResponseFieldDTO(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new UserResponseErrorDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validaçao",
                listErrors
        );
    }
}
