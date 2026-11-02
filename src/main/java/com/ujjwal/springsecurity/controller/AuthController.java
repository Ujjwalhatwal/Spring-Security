package com.ujjwal.springsecurity.controller;

import com.ujjwal.springsecurity.dto.AuthRequest;
import com.ujjwal.springsecurity.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        // Logic to generate JWT token based on the provided username and password
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword())
            );

            // Generate JWT token here and return it
            return jwtUtil.generateToken(authRequest.getUsername());
        } catch (Exception ex) {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
