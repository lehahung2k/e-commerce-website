package com.hunglh.backend.services.impl;

import com.hunglh.backend.dto.user.RegisterForm;
import com.hunglh.backend.dto.user.UserResponse;
import com.hunglh.backend.dto.user.UserUpdate;
import com.hunglh.backend.entities.Cart;
import com.hunglh.backend.entities.Role;
import com.hunglh.backend.entities.Users;
import com.hunglh.backend.enums.Roles;
import com.hunglh.backend.repositories.CartRepository;
import com.hunglh.backend.repositories.UserRepository;
import com.hunglh.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public ResponseEntity<Object> createUser(RegisterForm user) {
        try {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
            }
            List<Role> list = new ArrayList<>();
            list.add(new Role(Roles.USER.name()));
            Users newUser = new Users();
            newUser.setRole(list);
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setAddress(user.getAddress());
            newUser.setCity(user.getCity());
            newUser.setCountry(user.getCountry());
            newUser.setPostIndex(user.getPostalCode());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setActive(true);

            // initial Cart
            Cart savedCart = cartRepository.save(new Cart(newUser));
            newUser.setCart(savedCart);

            userRepository.save(newUser);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @Override
    public void updatePassword(String newPassword, String email) {

    }

    @Override
    @Transactional
    public void updateProfile(UserUpdate userUpdate, String email) {
        Users oldUser = userRepository.findByEmail(email);
        oldUser.setFirstName(userUpdate.getFirstName());
        oldUser.setLastName(userUpdate.getLastName());
        oldUser.setPhoneNumber(userUpdate.getPhoneNumber());
        oldUser.setAddress(userUpdate.getAddress());
        oldUser.setCity(userUpdate.getCity());
        oldUser.setCountry(userUpdate.getCountry());
        oldUser.setPostIndex(userUpdate.getPostalCode());
        userRepository.save(oldUser);
    }

    @Override
    public ResponseEntity<Object> getInfo(String email) {
        try {
            Users user = userRepository.findByEmail(email);
            UserResponse response = convertToUserResponse(user);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("user", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<Object> findAllUsers() {
        try {
            List<Users> listUser = userRepository.findAllByRolesRole(Roles.USER.name());
            List<UserResponse> users = listUser.stream()
                    .map(this::convertToUserResponse)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("users", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Object> findAllEmployees() {
        try {
            List<Users> users = userRepository.findAllByRolesRole(Roles.EMPLOYEE.name());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("users", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    private UserResponse convertToUserResponse(Users user) {
        UserResponse response = new UserResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        response.setCity(user.getCity());
        response.setCountry(user.getCountry());
        response.setPostalCode(user.getPostIndex());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setActive(user.isActive());
        return response;
    }
}
