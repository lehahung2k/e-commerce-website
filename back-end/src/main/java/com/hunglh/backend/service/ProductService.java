package com.hunglh.backend.service;

import com.hunglh.backend.dto.product.ProductSearchRequest;
import com.hunglh.backend.entities.Products;
import com.hunglh.backend.enums.SearchProduct;
import com.hunglh.backend.repository.projection.ProductProjection;
import graphql.schema.DataFetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Products getProductById(Long perfumeId);

    Page<ProductProjection> getAllProducts(Pageable pageable);

    List<ProductProjection> getProductByIds(List<Long> perfumesId);

//    Page<ProductProjection> findProductsByFilterParams(ProductSearchRequest filter, Pageable pageable);

    List<Products> findByBrand(String brand);

    List<Products> findByModel(String model);
    
    Page<ProductProjection> findByInputText(SearchProduct searchType, String text, Pageable pageable);

    Products saveProduct(Products product, MultipartFile file);

    String deleteProduct(Long productId);

    DataFetcher<Products> getProductByQuery();

    DataFetcher<List<ProductProjection>> getAllProductsByQuery();

    DataFetcher<List<Products>> getAllProductsByIdsQuery();
}
