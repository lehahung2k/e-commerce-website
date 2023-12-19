package com.hunglh.backend.services;

import com.hunglh.backend.dto.authen.JwtResponse;
import com.hunglh.backend.dto.authen.LoginForm;

public interface AuthService {
    JwtResponse login(LoginForm loginForm);
}
