package com.hunglh.backend.service;

import com.hunglh.backend.entities.Products;
import com.hunglh.backend.entities.Users;
import graphql.schema.DataFetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Users getUserById(Long userId);

    Users getUserInfo(String email);
    
    Page<Users> getAllUsers(Pageable pageable);

    List<Products> getCart(List<Long> productIds);

    Users updateUserInfo(String email, Users user);

    DataFetcher<List<Users>> getAllUsersByQuery();

    DataFetcher<Users> getUserByQuery();
}
