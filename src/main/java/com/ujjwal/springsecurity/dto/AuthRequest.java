package com.ujjwal.springsecurity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthRequest {

    private String username;
    private String password;

}
