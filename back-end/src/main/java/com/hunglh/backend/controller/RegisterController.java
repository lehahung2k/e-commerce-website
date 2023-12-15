package com.hunglh.backend.controller;

import com.hunglh.backend.dto.auth.RegistrationRequest;
import com.hunglh.backend.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.hunglh.backend.constaints.PathContants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(REGISTER)
public class RegisterController {
    private final AuthMapper authenticationMapper;

    @PostMapping()
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationRequest user, BindingResult bindingResult) {
        System.out.println("register");
        return ResponseEntity.ok(authenticationMapper.registerUser(user, bindingResult));
    }

    @GetMapping(ACTIVATE_CODE)
    public ResponseEntity<String> activateEmailCode(@PathVariable String code) {
        return ResponseEntity.ok(authenticationMapper.activateUser(code));
    }
}
