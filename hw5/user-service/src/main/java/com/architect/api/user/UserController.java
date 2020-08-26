package com.architect.api.user;

import com.architect.api.user.dto.UpdateUserRequest;
import com.architect.api.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> fetchUser(HttpServletRequest request) {
        String userId = request.getHeader("x-user-id");
        String email = request.getHeader("x-user-email");
        String firstName = request.getHeader("x-user-first-name");
        String lastName = request.getHeader("x-user-last-name");

        if (userId == null) {
            return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
        }

        UserResponse response = UserResponse.builder()
                .id(userId)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> userResponse(HttpServletRequest request, @RequestBody UpdateUserRequest updateUserRequest) {
        String userId = request.getHeader("x-user-id");
        if (userId == null) {
            return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
        }
        userService.updateUser(Long.valueOf(userId), updateUserRequest);
        return ResponseEntity.ok().build();
    }
}
