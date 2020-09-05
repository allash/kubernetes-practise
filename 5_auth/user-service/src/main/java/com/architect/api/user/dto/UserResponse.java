package com.architect.api.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private final String id;
    private final String email;
    private final String firstName;
    private final String lastName;
}
