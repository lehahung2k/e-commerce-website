package com.hunglh.backend.dto.auth;

import com.hunglh.backend.dto.user.UserResponse;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private UserResponse user;
    private String token;
}
