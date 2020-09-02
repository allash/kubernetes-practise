package com.architect.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class EntityNotFoundException extends RuntimeException {
    private final String message;
}
