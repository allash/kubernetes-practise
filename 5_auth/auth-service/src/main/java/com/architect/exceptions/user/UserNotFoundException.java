package com.architect.exceptions.user;

import com.architect.exceptions.EntityNotFoundException;

public abstract class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
