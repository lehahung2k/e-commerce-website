package com.hunglh.backend.services;

import com.hunglh.backend.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Products findOne(Long productId);

    // All selling products
    Page<Products> findUpAll(Pageable pageable);
    // All products
    Page<Products> findAll(Pageable pageable);
    // All products in a category
    Page<Products> findAllInBrand(Integer brandType, Pageable pageable);

    // increase stock
    void increaseStock(Long productId, int amount);

    //decrease stock
    void decreaseStock(Long productId, int amount);

    Products offSale(Long productId);

    Products onSale(Long productId);

    Products update(Products product);

    Products save(Products product);

    void delete(Long productId);

}
