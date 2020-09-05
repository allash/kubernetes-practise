package com.architect.api.user;

import com.architect.api.user.dto.request.DtoUpdateUserRequest;
import com.architect.api.user.dto.response.DtoGetUserResponse;
import com.architect.api.user.dto.request.DtoCreateUserRequest;
import com.architect.exceptions.user.UserNotFoundByIdException;
import com.architect.persistence.entities.DbUser;
import com.architect.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<DtoGetUserResponse> fetchUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public DtoGetUserResponse fetchUserById(long id) {
        DbUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));
        return userMapper.toDto(user);
    }

    public DtoGetUserResponse createUser(DtoCreateUserRequest request) {
        DbUser user = new DbUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public DtoGetUserResponse updateUserById(Long id, DtoUpdateUserRequest request) {
        DbUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));

        user.setPhone(request.getPhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return userMapper.toDto(userRepository.save(user));
    }

    public void deleteUserById(Long id) {
        DbUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));

        userRepository.delete(user);
    }
}
