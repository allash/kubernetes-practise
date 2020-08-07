package com.architect.api.user;

import com.architect.api.user.dto.request.DtoCreateUserRequest;
import com.architect.api.user.dto.request.DtoUpdateUserRequest;
import com.architect.api.user.dto.response.DtoGetUserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<DtoGetUserResponse> fetchUsers() {
        return userService.fetchUsers();
    }

    @GetMapping("/{id}")
    public DtoGetUserResponse fetchUserById(@PathVariable long id) {
        return userService.fetchUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoGetUserResponse createUser(@RequestBody @Validated DtoCreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public DtoGetUserResponse updateUserById(@PathVariable long id, @RequestBody @Validated DtoUpdateUserRequest request) {
        return userService.updateUserById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
    }
}
