package com.hunglh.backend.services;

import com.hunglh.backend.dto.product.NewProduct;
import com.hunglh.backend.entities.Products;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ProductService {

    ResponseEntity<Object> findOne(Long productId);

    // All selling products
    Page<Products> findUpAll(Pageable pageable);
    // All products
    ResponseEntity<Object> findAll(Pageable pageable);
    // All products in a category
    ResponseEntity<Object> findAllInBrand(Integer brandType, Pageable pageable);

    // increase stock
    void increaseStock(Long productId, int amount);

    //decrease stock
    void decreaseStock(Long productId, int amount);

    Products offSale(Long productId);

    Products onSale(Long productId);

    Products update(Products product);

    Products save(NewProduct newProduct);

    ResponseEntity<Object> searchProducts(String keyword, Pageable pageable);

    void delete(Long productId);

}
