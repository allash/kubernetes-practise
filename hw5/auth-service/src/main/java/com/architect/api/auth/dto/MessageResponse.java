package com.architect.api.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponse {
    private final String message;
}
