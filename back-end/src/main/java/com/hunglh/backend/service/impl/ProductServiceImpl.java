package com.hunglh.backend.service.impl;

import com.hunglh.backend.dto.product.ProductSearchRequest;
import com.hunglh.backend.entities.Products;
import com.hunglh.backend.enums.SearchProduct;
import com.hunglh.backend.exception.ApiRequestException;
import com.hunglh.backend.repository.ProductRepository;
import com.hunglh.backend.repository.projection.ProductProjection;
import com.hunglh.backend.service.ProductService;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.hunglh.backend.constaints.MessageConstants.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
//    private final AmazonS3 amazonS3client;

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @Override
    public Products getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<ProductProjection> getAllProducts(Pageable pageable) {
        return productRepository.findAllByOrderByIdAsc(pageable);
    }

    @Override
    public List<ProductProjection> getProductByIds(List<Long> productsId) {
        return productRepository.getPerfumesByIds(productsId);
    }

    @Override
    public Page<ProductProjection> findProductsByFilterParams(ProductSearchRequest filter, Pageable pageable) {
        return productRepository.findProductsByFilterParams(
                filter.getBrand(),
                filter.getModel(),
                filter.getStorageCapacity(),
                filter.getRamCapacity(),
                filter.getPrices().get(0),
                filter.getPrices().get(1),
                filter.getSortByPrice(),
                pageable);
    }

    @Override
    public List<Products> findByBrand(String brand) {
        return productRepository.findByBrandName(brand);
    }

    @Override
    public List<Products> findByModel(String model) {
        return productRepository.findByModelName(model);
    }

    @Override
    public Page<ProductProjection> findByInputText(SearchProduct searchType, String text, Pageable pageable) {
        if (searchType.equals(SearchProduct.BRAND)) {
            return productRepository.findByBrand(text, pageable);
        } else if (searchType.equals(SearchProduct.MODEL)) {
            return productRepository.findByModel(text, pageable);
        } else {
            return productRepository.findByProductName(text, pageable);
        }
    }

    @Override
    @Transactional
    public Products saveProduct(Products perfume, MultipartFile multipartFile) {
//        if (multipartFile == null) {
//            perfume.setFilename(amazonS3client.getUrl(bucketName, "empty.jpg").toString());
//        } else {
//            File file = new File(multipartFile.getOriginalFilename());
//            try (FileOutputStream fos = new FileOutputStream(file)) {
//                fos.write(multipartFile.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String fileName = UUID.randomUUID().toString() + "." + multipartFile.getOriginalFilename();
//            amazonS3client.putObject(new PutObjectRequest(bucketName, fileName, file));
//            perfume.setFilename(amazonS3client.getUrl(bucketName, fileName).toString());
//            file.delete();
//        }
        return productRepository.save(perfume);
    }

    @Override
    @Transactional
    public String deleteProduct(Long productId) {
        Products products = productRepository.findById(productId)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        productRepository.delete(products);
        return "Deleted successfully";
    }

    @Override
    public DataFetcher<Products> getProductByQuery() {
        return dataFetchingEnvironment -> {
            Long productId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return productRepository.findById(productId).get();
        };
    }

    @Override
    public DataFetcher<List<ProductProjection>> getAllProductsByQuery() {
        return dataFetchingEnvironment -> productRepository.findAllByOrderByIdAsc();
    }

    @Override
    public DataFetcher<List<Products>> getAllProductsByIdsQuery() {
        return dataFetchingEnvironment -> {
            List<String> objects = dataFetchingEnvironment.getArgument("ids");
            List<Long> perfumesId = objects.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            return productRepository.findByIdIn(perfumesId);
        };
    }
}
