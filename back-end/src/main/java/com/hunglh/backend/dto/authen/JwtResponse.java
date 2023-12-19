package com.hunglh.backend.dto.authen;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String fullName;
    private String role;

    public JwtResponse(String token, String email, String fullName, String role) {
        this.email = email;
        this.fullName = fullName;
        this.token = token;
        this.role = role;
    }
}
