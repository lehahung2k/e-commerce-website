package com.hunglh.backend.services;

import com.hunglh.backend.dto.RegisterForm;
import com.hunglh.backend.entities.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    String createUser(RegisterForm user);
    void updatePassword(String newPassword, String email);

    void updateProfile(String fullName, String email);

    Users getUserByEmail(String email);

    List<Users> getAllUsers();
}
