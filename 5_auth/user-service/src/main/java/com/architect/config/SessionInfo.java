package com.architect.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SessionInfo {
    private final String token;
    private final Long userId;
    private final String email;
    private final String firstName;
    private final String lastName;
}
