package com.hunglh.backend.repositories;

import com.hunglh.backend.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
    Products findByProductId(Long productId);
    // onsale product
    Page<Products> findAllByProductStatusOrderByProductIdAsc(Integer productStatus, Pageable pageable);

    // product in one category
    Page<Products> findAllByBrandOrderByProductIdAsc(Integer brandType, Pageable pageable);

    Page<Products> findAllByOrderByProductId(Pageable pageable);

    Products findByProductNameAndModelAndColorAndStorageCapacity(String productName, String model, String color, String storageCapacity);

    Page<Products> findByProductNameContainingOrModelContaining(String productName, String model, Pageable pageable);

}
