package com.architect.exceptions;

public class AccountNotFoundByIdException extends AccountNotFoundException {
    public AccountNotFoundByIdException(Long id) {
        super("Account not found by id: " + id);
    }
}
