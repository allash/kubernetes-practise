package com.architect.exceptions;

public abstract class AccountNotFoundException extends EntityNotFoundException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
