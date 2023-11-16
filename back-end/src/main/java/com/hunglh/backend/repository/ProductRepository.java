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

    @Query("SELECT product FROM Products product WHERE product.brand = :brand")
    List<Products> findByBrandName(String brand);

    @Query("SELECT product FROM Products product WHERE product.model = :model")
    List<Products> findByModelName(String model);

    @Query("SELECT product FROM Products product " +
            "WHERE (coalesce(:brand, null) IS NULL OR product.brand IN :brand) " +
            "AND (coalesce(:model, null) IS NULL OR product.model IN :model) " +
            "AND (coalesce(:storageCapacity, null) IS NULL OR product.storageCapacity IN :storageCapacity) " +
            "AND (coalesce(:ram, null) IS NULL OR product.ram IN :ram) " +
            "AND (coalesce(:priceStart, null) IS NULL OR product.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY CASE WHEN :sortByPrice = true THEN product.price ELSE -product.price END ASC")
    Page<ProductProjection> findProductsByFilterParams(
            List<String> brands,
            List<String> models,
            List<String> storageCapacities,
            List<String> ramCapacities,
            Integer priceStart,
            Integer priceEnd,
            boolean sortByPrice,
            Pageable pageable);

    @Query("SELECT product FROM Products product " +
            "WHERE UPPER(product.brand) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByBrand(String text, Pageable pageable);

    @Query("SELECT product FROM Products product " +
            "WHERE UPPER(product.model) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByModel(String text, Pageable pageable);

    @Query("SELECT product FROM Products product " +
            "WHERE UPPER(product.productName) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY product.price DESC")
    Page<ProductProjection> findByProductName(String text, Pageable pageable);
}
