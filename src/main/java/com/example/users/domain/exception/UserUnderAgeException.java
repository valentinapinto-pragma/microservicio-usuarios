package com.example.users.domain.exception;

public class UserUnderAgeException extends RuntimeException {
    public UserUnderAgeException() {
        super("El usuario debe ser mayor de edad.");
    }
}
