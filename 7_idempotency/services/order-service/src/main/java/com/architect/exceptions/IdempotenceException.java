package com.architect.exceptions;

public abstract class IdempotenceException extends ServiceException {
    public IdempotenceException(String message) {
        super(message);
    }
}
