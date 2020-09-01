package com.architect.api.user;

import com.architect.api.user.dto.response.GetUserResponse;
import com.architect.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public GetUserResponse toDto(UserEntity user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .username(user.getUsername())
                .build();
    }
}
