package com.hunglh.backend.service.impl;

import com.hunglh.backend.entities.Products;
import com.hunglh.backend.entities.Users;
import com.hunglh.backend.exception.ApiRequestException;
import com.hunglh.backend.repository.ProductRepository;
import com.hunglh.backend.repository.UserRepository;
import com.hunglh.backend.service.UserService;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hunglh.backend.constaints.MessageConstants.EMAIL_NOT_FOUND;
import static com.hunglh.backend.constaints.MessageConstants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public Users getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Users getUserInfo(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<Users> getAllUsers(Pageable pageable) {
        try {
            return userRepository.findAllByOrderByIdAsc(pageable);
        } catch (Exception e) {
            throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Products> getCart(List<Long> productIds) {
        return productRepository.findByIdIn(productIds);
    }

    @Override
    @Transactional
    public Users updateUserInfo(String email, Users user) {
        Users userFromDb = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setCity(user.getCity());
        userFromDb.setAddress(user.getAddress());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setPostIndex(user.getPostIndex());
        return userFromDb;
    }
    
    @Override
    public DataFetcher<Users> getUserByQuery() {
        return dataFetchingEnvironment -> {
            Long userId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return userRepository.findById(userId).get();
        };
    }

    @Override
    public DataFetcher<List<Users>> getAllUsersByQuery() {
        return dataFetchingEnvironment -> userRepository.findAllByOrderByIdAsc();
    }
}
