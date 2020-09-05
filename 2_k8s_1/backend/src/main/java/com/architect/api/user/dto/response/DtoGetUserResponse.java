package com.architect.api.user.dto.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class DtoGetUserResponse {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
}
