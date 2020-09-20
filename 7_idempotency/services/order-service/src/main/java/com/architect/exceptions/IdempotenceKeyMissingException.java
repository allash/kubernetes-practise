package com.architect.exceptions;

public class IdempotenceKeyMissingException extends IdempotenceException {
    public IdempotenceKeyMissingException() {
        super("idempotence_key_missing");
    }
}
