package com.architect.api.user;

import com.architect.api.user.dto.request.CreateUserRequest;
import com.architect.api.user.dto.request.UpdateUserRequest;
import com.architect.api.user.dto.response.GetUserResponse;
import com.architect.domain.CreateBillingAccountRequest;
import com.architect.exceptions.user.UserNotFoundByIdException;
import com.architect.http.clients.BillingClient;
import com.architect.persistence.UserRepository;
import com.architect.persistence.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BillingClient billingClient;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       BillingClient billingClient) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.billingClient = billingClient;
    }

    public List<GetUserResponse> fetchUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public GetUserResponse fetchUserById(long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));
        return userMapper.toDto(user);
    }

    @Transactional
    public GetUserResponse createUser(CreateUserRequest request) {
        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());

        UserEntity created = userRepository.save(user);

        CreateBillingAccountRequest createBillingAccountRequest = CreateBillingAccountRequest.builder()
                .email(created.getEmail())
                .userId(created.getId())
                .build();
        billingClient.createBillingAccount(createBillingAccountRequest);

        return userMapper.toDto(user);
    }

    public GetUserResponse updateUserById(Long id, UpdateUserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));

        user.setPhone(request.getPhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return userMapper.toDto(userRepository.save(user));
    }

    public void deleteUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));

        userRepository.delete(user);
    }
}
