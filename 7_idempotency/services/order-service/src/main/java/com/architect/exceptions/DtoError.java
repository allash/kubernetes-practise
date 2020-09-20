package com.architect.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoError {
    private final int code;
    private final String message;
}
