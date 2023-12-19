package com.hunglh.backend.controllers;

import com.hunglh.backend.dto.authen.JwtResponse;
import com.hunglh.backend.dto.authen.LoginForm;
import com.hunglh.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginForm loginForm){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginForm));
    }
}
