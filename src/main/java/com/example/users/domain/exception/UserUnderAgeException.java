package com.example.users.domain.exception;

public class UserUnderAgeException extends RuntimeException {
  public UserUnderAgeException(String message) {
    super(message);
  }
}
