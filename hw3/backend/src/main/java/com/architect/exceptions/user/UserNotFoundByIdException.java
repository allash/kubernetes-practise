package com.architect.exceptions.user;

public class UserNotFoundByIdException extends UserNotFoundException {
    public UserNotFoundByIdException(long id) {
        super(String.format("User not found by %d", id));
    }
}
