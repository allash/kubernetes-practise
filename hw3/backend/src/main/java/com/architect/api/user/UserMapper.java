package com.architect.api.user;

import com.architect.api.user.dto.response.DtoGetUserResponse;
import com.architect.persistence.entities.DbUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public DtoGetUserResponse toDto(DbUser user) {
        return DtoGetUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .username(user.getUsername())
                .build();
    }
}
