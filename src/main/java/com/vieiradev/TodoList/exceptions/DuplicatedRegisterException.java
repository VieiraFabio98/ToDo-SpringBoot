package com.vieiradev.TodoList.exceptions;

public class DuplicatedRegisterException extends RuntimeException{
    public DuplicatedRegisterException(String message) {
        super(message);
    }
}
