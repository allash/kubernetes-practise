package com.architect.exceptions.user;

public class UserNotFoundByEmailException extends UserNotFoundException {
    public UserNotFoundByEmailException(String email) {
        super(String.format("User not found by email: %s", email));
    }
}
