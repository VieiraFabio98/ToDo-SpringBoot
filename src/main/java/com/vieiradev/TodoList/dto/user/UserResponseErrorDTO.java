package com.vieiradev.TodoList.dto.user;

import org.springframework.http.HttpStatus;

import java.util.List;

public record UserResponseErrorDTO(int status, String message, List<UserResponseFieldDTO> errors) {

    public static UserResponseErrorDTO badRequest(String message) {
        return new UserResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static UserResponseErrorDTO conflictError(String message) {
        return new UserResponseErrorDTO(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
