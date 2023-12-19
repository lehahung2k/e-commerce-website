package com.hunglh.backend.repositories;

import com.hunglh.backend.entities.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {
    // Some category
    List<ProductBrand> findByBrandTypeInOrderByBrandTypeAsc(List<Integer> brandTypes);
    // All category
    List<ProductBrand> findAllByOrderByBrandType();
    // One category
    ProductBrand findByBrandType(Integer brandType);
}
