package com.architect.api.user;

import com.architect.api.user.dto.UpdateUserRequest;
import com.architect.exceptions.user.UserNotFoundByIdException;
import com.architect.http.clients.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AuthClient authClient;

    @Autowired
    public UserService(AuthClient authClient) {
        this.authClient = authClient;
    }

    void updateUser(Long id, UpdateUserRequest updateUserRequest) {
        if (!authClient.updateUser(id, updateUserRequest)) {
            throw new UserNotFoundByIdException(id);
        }
    }
}
