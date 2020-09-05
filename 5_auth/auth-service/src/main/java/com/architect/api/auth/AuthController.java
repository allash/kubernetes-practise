package com.architect.api.auth;

import com.architect.api.auth.dto.*;
import com.architect.config.SessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Validated @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    RegisterResponse register(@Validated @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @GetMapping("/auth")
    ResponseEntity<?> auth() {
        SessionInfo sessionInfo = (SessionInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-user-id", sessionInfo.getUserId().toString());
        httpHeaders.add("x-user-email", sessionInfo.getEmail());
        httpHeaders.add("x-user-first-name", sessionInfo.getFirstName());
        httpHeaders.add("x-user-last-name", sessionInfo.getLastName());

        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/signin")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    MessageResponse signin() {
        return MessageResponse.builder()
                .message("Please provide valid credentials!")
                .build();
    }

    // for some reason /logout does not work...
    @DeleteMapping("/user-logout")
    void logout() {
        SessionInfo sessionInfo = (SessionInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authService.logout(sessionInfo.getUserId());
    }

    //TODO: move into internal controller
    @PutMapping("/internal/users/{id}")
    void updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        authService.updateUser(id, request);
    }
}
