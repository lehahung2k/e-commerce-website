package com.hunglh.backend.repository;

import com.hunglh.backend.entities.Products;
import com.hunglh.backend.repository.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    List<ProductProjection> findAllByOrderByIdAsc();
    
    @Query("SELECT product FROM Products product ORDER BY product.id ASC")
    Page<ProductProjection> findAllByOrderByIdAsc(Pageable pageable);

    List<Products> findByIdIn(List<Long> productsIds);

    @Query("SELECT product FROM Products product WHERE product.id IN :productsIds")
    List<ProductProjection> getPerfumesByIds(List<Long> productsIds);
}
