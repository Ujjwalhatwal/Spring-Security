package com.ujjwal.springsecurity.controller;

import com.ujjwal.springsecurity.dto.RegisterUserRequest;
import com.ujjwal.springsecurity.dto.Role;
import com.ujjwal.springsecurity.dto.UserResponse;
import com.ujjwal.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        registerUserRequest.setRole(Role.USER);
        UserResponse registeredUser = userService.registerUser(registerUserRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create-user")
    public ResponseEntity<UserResponse> registerByAdmin(@RequestBody RegisterUserRequest registerUserRequest) {
        UserResponse registeredAdmin = userService.registerUser(registerUserRequest);
        return ResponseEntity.ok(registeredAdmin);
    }

}
