package com.architect.exceptions;

public class IdempotenceKeyConflictException extends IdempotenceException {

    public IdempotenceKeyConflictException() {
        super("duplicated_request");
    }
}
