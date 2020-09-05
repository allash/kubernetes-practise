package com.architect.api.user;

import com.architect.api.user.dto.request.CreateUserRequest;
import com.architect.api.user.dto.request.UpdateUserRequest;
import com.architect.api.user.dto.response.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<GetUserResponse> fetchUsers() {
        return userService.fetchUsers();
    }

    @GetMapping("/{id}")
    public GetUserResponse fetchUserById(@PathVariable long id) {
        return userService.fetchUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetUserResponse createUser(@RequestBody @Validated CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public GetUserResponse updateUserById(@PathVariable long id, @RequestBody @Validated UpdateUserRequest request) {
        return userService.updateUserById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
    }
}
