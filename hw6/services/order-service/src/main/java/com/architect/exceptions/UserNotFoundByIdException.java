package com.architect.exceptions;

public class UserNotFoundByIdException extends UserNotFoundException {
    public UserNotFoundByIdException(Long id) {
        super(String.format("User not found by id: %s", id));
    }
}
