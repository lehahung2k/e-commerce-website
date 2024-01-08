package com.hunglh.backend.services;

import com.hunglh.backend.dto.user.RegisterForm;
import com.hunglh.backend.dto.user.UserUpdate;
import com.hunglh.backend.entities.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<Object> createUser(RegisterForm user);
    void updatePassword(String newPassword, String email);

    void updateProfile(UserUpdate userUpdate, String email);

    ResponseEntity<Object> getInfo(String email);

    Users getUserByEmail(String email);

    ResponseEntity<Object> findAllUsers();

    ResponseEntity<Object> findAllEmployees();
}
