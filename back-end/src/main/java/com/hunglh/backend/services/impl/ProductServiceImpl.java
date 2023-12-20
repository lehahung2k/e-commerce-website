package com.hunglh.backend.services.impl;

import com.hunglh.backend.entities.Products;
import com.hunglh.backend.enums.ProductStatusEnum;
import com.hunglh.backend.repositories.ProductRepository;
import com.hunglh.backend.services.ProductBrandService;
import com.hunglh.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductBrandService productBrandService;

    @Override
    public Products findOne(Long productId) {
        try {
            return productRepository.findByProductId(productId);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public Page<Products> findUpAll(Pageable pageable) {
        try {
            return productRepository.findAllByProductStatusOrderByProductIdAsc(ProductStatusEnum.UP.getCode(), pageable);
        } catch (Exception e) {
            throw new RuntimeException("Products not found");
        }
    }

    @Override
    public Page<Products> findAll(Pageable pageable) {
        try {
            return productRepository.findAllByOrderByProductId(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Products not found");
        }
    }

    @Override
    public Page<Products> findAllInBrand(Integer brandType, Pageable pageable) {
        try {
            return productRepository.findAllByBrandOrderByProductIdAsc(brandType, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Products not found");
        }
    }

    @Override
    @Transactional
    public void increaseStock(Long productId, int amount) {
        try {
            Products product = findOne(productId);
            if (product == null) throw new RuntimeException("Product not found");

            int update = product.getQuantityInStock() + amount;
            product.setQuantityInStock(update);
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    @Transactional
    public void decreaseStock(Long productId, int amount) {
        try {
            Products product = findOne(productId);
            if (product == null) throw new RuntimeException("Product not found");

            int update = product.getQuantityInStock() - amount;
            if (update <= 0) throw new RuntimeException("Product not enough");

            product.setQuantityInStock(update);
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    @Transactional
    public Products offSale(Long productId) {
        try {
            Products product = findOne(productId);
            if (product == null) throw new RuntimeException("Product not found");

            if (product.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())) {
                throw new RuntimeException("Product status error");
            }

            //update
            product.setProductStatus(ProductStatusEnum.DOWN.getCode());
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    @Transactional
    public Products onSale(Long productId) {
        try {
            Products product = findOne(productId);
            if (product == null) throw new RuntimeException("Product not found");

            if (product.getProductStatus().equals(ProductStatusEnum.UP.getCode())) {
                throw new RuntimeException("Product status error");
            }

            //update
            product.setProductStatus(ProductStatusEnum.UP.getCode());
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public Products update(Products product) {
        try {
            productBrandService.findByBrandType(product.getBrand());
            if (Objects.isNull(product.getProductId())) throw new RuntimeException("Product not found");
            if (product.getProductStatus() > 1 || product.getProductStatus() < 0) throw new RuntimeException("Product status error");
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public Products save(Products product) {
        return update(product);
    }

    @Override
    public void delete(Long productId) {
        try{
            Products product = findOne(productId);
            if (product == null) throw new RuntimeException("Product not found");
            productRepository.delete(product);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }
}
