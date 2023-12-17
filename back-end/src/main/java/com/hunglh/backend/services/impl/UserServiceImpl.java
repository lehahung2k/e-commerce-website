package com.hunglh.backend.services.impl;

import com.hunglh.backend.dto.RegisterForm;
import com.hunglh.backend.entities.Users;
import com.hunglh.backend.enums.Role;
import com.hunglh.backend.repository.UserRepository;
import com.hunglh.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String createUser(RegisterForm user) {
        try {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new RuntimeException("Email already exist");
            }
            Users newUser = new Users();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setAddress(user.getAddress());
            newUser.setCity(user.getCity());
            newUser.setCountry(user.getCountry());
            newUser.setPostIndex(user.getPostalCode());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setRoles(Collections.singleton(Role.USER));
            newUser.setActive(true);

            userRepository.save(newUser);
            return "User created successfully";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePassword(String newPassword, String email) {

    }

    @Override
    public void updateProfile(String fullName, String email) {

    }

    @Override
    public Users getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<Users> getAllUsers() {
        return null;
    }
}
