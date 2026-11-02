package com.ujjwal.springsecurity.service;

import com.ujjwal.springsecurity.dto.RegisterUserRequest;
import com.ujjwal.springsecurity.dto.UserResponse;
import com.ujjwal.springsecurity.entity.User;
import com.ujjwal.springsecurity.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse registerUser(RegisterUserRequest registerUserRequest){
        // check if user already exists
        if(userDetailsRepository.findByUsername(registerUserRequest.getUsername()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        // encode password
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setRole(registerUserRequest.getRole());
        String encode = passwordEncoder.encode(registerUserRequest.getPassword());
        user.setPassword(encode);
        // save user to database
        User savedUser = userDetailsRepository.save(user);
        return new UserResponse(user.getId(), user.getUsername(), user.getRole().name());
    }
}
