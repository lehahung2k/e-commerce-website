package com.hunglh.backend.services.impl;

import com.hunglh.backend.security.JwtProvider;
import com.hunglh.backend.dto.authen.JwtResponse;
import com.hunglh.backend.dto.authen.LoginForm;
import com.hunglh.backend.entities.Users;
import com.hunglh.backend.repositories.UserRepository;
import com.hunglh.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    @Override
    public JwtResponse login(LoginForm loginForm) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
            Users user = userRepository.findByEmail(loginForm.getEmail());
            String jwt = jwtProvider.generateToken(user);
            String userRole = user.getRole().isEmpty() ? null : user.getRole().get(0).getRole();
            return new JwtResponse(jwt, user.getEmail(), user.getFirstName() + " " + user.getLastName(), userRole);
        } catch (Exception e) {
            throw new ProviderNotFoundException(e.getMessage());
        }
    }
}
