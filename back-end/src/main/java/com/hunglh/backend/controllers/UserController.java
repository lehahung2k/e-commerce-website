package com.hunglh.backend.controllers;

import com.hunglh.backend.dto.user.RegisterForm;
import com.hunglh.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterForm registerForm) {
        return this.userService.createUser(registerForm);
    }

    @GetMapping("/manager/user")
    public ResponseEntity<Object> findAllUser() {
        return null;
    }

    @GetMapping("/manager/employee")
    public ResponseEntity<Object> findAllEmployee() {
        return null;
    }
}
