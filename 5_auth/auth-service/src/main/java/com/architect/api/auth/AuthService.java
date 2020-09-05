package com.architect.api.auth;

import com.architect.api.auth.dto.*;
import com.architect.exceptions.EmailOrPasswordNotMatchException;
import com.architect.exceptions.UnauthorizedException;
import com.architect.exceptions.user.UserNotFoundByEmailException;
import com.architect.exceptions.user.UserNotFoundByIdException;
import com.architect.persistence.SessionRepository;
import com.architect.persistence.UserRepository;
import com.architect.persistence.entities.DbSession;
import com.architect.persistence.entities.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class AuthService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    AuthService(SessionRepository sessionRepository,
                UserRepository userRepository,
                PasswordEncoder passwordEncoder) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    LoginResponse login(LoginRequest loginRequest) {
        DbUser user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(EmailOrPasswordNotMatchException::new);
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new EmailOrPasswordNotMatchException();
        }

        String token = UUID.randomUUID().toString();
        DbSession session = new DbSession();
        session.setToken(token);
        session.setUser(user);

        sessionRepository.save(session);

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    RegisterResponse register(RegisterRequest registerRequest) {
        DbUser user = new DbUser();
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername((registerRequest.getFirstName() + "_" + registerRequest.getLastName()).toLowerCase());
        user.setPhone("12345");

        DbUser createdUser = userRepository.save(user);

        String token = UUID.randomUUID().toString();
        DbSession session = new DbSession();
        session.setToken(token);
        session.setUser(createdUser);

        sessionRepository.save(session);

        return RegisterResponse.builder()
                .id(createdUser.getId())
                .build();
    }

    void logout(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundByIdException(userId));
        sessionRepository.removeByUserId(userId);
    }

    //TODO: move into internal service
    void updateUser(Long id, UpdateUserRequest request) {
        DbUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundByIdException(id));
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        userRepository.save(user);
    }
}
