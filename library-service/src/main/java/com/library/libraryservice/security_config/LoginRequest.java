package com.library.libraryservice.security_config;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
