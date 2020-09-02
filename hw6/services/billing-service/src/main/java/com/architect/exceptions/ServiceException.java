package com.architect.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class ServiceException extends RuntimeException {
    private final String message;
}
