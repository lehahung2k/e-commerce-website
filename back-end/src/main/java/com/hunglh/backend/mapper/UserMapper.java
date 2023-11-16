package com.hunglh.backend.mapper;

import com.hunglh.backend.dto.HeaderResponse;
import com.hunglh.backend.dto.product.ProductResponse;
import com.hunglh.backend.dto.user.BaseUserResponse;
import com.hunglh.backend.dto.user.UpdateUserRequest;
import com.hunglh.backend.dto.user.UserResponse;
import com.hunglh.backend.entities.Users;
import com.hunglh.backend.exception.InputFieldException;
import com.hunglh.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final CommonMapper commonMapper;
    private final UserService userService;

    public UserResponse getUserById(Long userId) {
        return commonMapper.convertToResponse(userService.getUserById(userId), UserResponse.class);
    }

    public UserResponse getUserInfo(String email) {
        return commonMapper.convertToResponse(userService.getUserInfo(email), UserResponse.class);
    }

    public List<ProductResponse> getCart(List<Long> productsIds) {
        return commonMapper.convertToResponseList(userService.getCart(productsIds), ProductResponse.class);
    }

    public HeaderResponse<BaseUserResponse> getAllUsers(Pageable pageable) {
        Page<Users> users = userService.getAllUsers(pageable);
        return commonMapper.getHeaderResponse(users.getContent(), users.getTotalPages(), users.getTotalElements(), BaseUserResponse.class);
    }

    public UserResponse updateUserInfo(String email, UpdateUserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Users user = commonMapper.convertToEntity(userRequest, Users.class);
        return commonMapper.convertToResponse(userService.updateUserInfo(email, user), UserResponse.class);
    }
}
