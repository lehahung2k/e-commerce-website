package com.hunglh.backend.services;

import com.hunglh.backend.dto.authen.LoginForm;

public interface AuthService {
    String login(LoginForm loginForm);
}
