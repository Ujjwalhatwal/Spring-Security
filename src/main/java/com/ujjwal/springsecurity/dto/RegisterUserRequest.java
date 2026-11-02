package com.ujjwal.springsecurity.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String password;
    private Role role;
}
