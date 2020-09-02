package com.architect.exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends ServiceException {
    public InsufficientFundsException(Long id, BigDecimal amount) {
        super(String.format("Account balance by id %s is lower than: %s", id, amount));
    }
}
