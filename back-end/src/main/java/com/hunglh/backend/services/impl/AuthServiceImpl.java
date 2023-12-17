package com.hunglh.backend.services.impl;

import com.hunglh.backend.dto.authen.LoginForm;
import com.hunglh.backend.entities.Users;
import com.hunglh.backend.repository.UserRepository;
import com.hunglh.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String login(LoginForm loginForm) {
        try {
            Users user = userRepository.findByEmail(loginForm.getEmail());
            if (user == null) {
                return "Login fail. Please check your email and password";
            }
            if (!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
                return "Login fail. Please check your email and password";
            }
            return "Login success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
