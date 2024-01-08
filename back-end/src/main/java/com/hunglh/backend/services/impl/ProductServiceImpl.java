package com.hunglh.backend.services.impl;

import com.hunglh.backend.dto.product.NewProduct;
import com.hunglh.backend.entities.Products;
import com.hunglh.backend.enums.ProductStatusEnum;
import com.hunglh.backend.repositories.ProductRepository;
import com.hunglh.backend.services.ProductBrandService;
import com.hunglh.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductBrandService productBrandService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/upload/images";
    public static String storeImageUrl = "http://localhost:1103/api" + "/upload/images/";


    @Override
    public ResponseEntity<Object> findOne(Long productId) {
        try {
            Products product = productRepository.findByProductId(productId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("mobile", product));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
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
    public ResponseEntity<Object> findAll(Pageable pageable) {
        try {
            Page<Products> products = productRepository.findAllByOrderByProductId(pageable);
            System.out.println(products.getTotalElements());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "products", products,
                    "totalPages", products.getTotalPages(),
                    "totalElements", products.getTotalElements()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Object> findAllInBrand(Integer brandType, Pageable pageable) {
        try {
            Page<Products> products = productRepository.findAllByBrandOrderByProductIdAsc(brandType, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "products", products,
                    "totalPages", products.getTotalPages(),
                    "totalElements", products.getTotalElements()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public void increaseStock(Long productId, int amount) {
        try {
            Products product = productRepository.findByProductId(productId);
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
            Products product = productRepository.findByProductId(productId);
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
            Products product = productRepository.findByProductId(productId);
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
            Products product = productRepository.findByProductId(productId);
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
            if (product.getProductStatus() > 1 || product.getProductStatus() < 0) throw new RuntimeException("Product status error");
            productRepository.save(product);
            return product;
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    @Transactional
    public Products save(NewProduct newProduct) {
        try {
            Products existingProduct = findSimilarProduct(newProduct);

            if (existingProduct != null) {
                existingProduct.setQuantityInStock(existingProduct.getQuantityInStock() + newProduct.getQuantityInStock());
                productRepository.save(existingProduct);
                return existingProduct;
            }

            Products product = new Products();
            MultipartFile file = newProduct.getImgFile();

            if (file != null && !file.isEmpty()) {
                product.setFileName(storeImage(file));
            } else {
                product.setFileName("/images/default.jpg");
            }

            if (newProduct.getQuantityInStock() == 0) product.setProductStatus(ProductStatusEnum.DOWN.getCode());
            product.setProductStatus(ProductStatusEnum.UP.getCode());
            product.setProductName(newProduct.getProductName());
            product.setModel(newProduct.getModel());
            product.setBrand(newProduct.getBrand());
            product.setColor(newProduct.getColor());
            product.setStorageCapacity(newProduct.getStorageCapacity());
            product.setQuantityInStock(newProduct.getQuantityInStock());
            product.setDescription(newProduct.getDescription());
            product.setPrice(newProduct.getPrice());
            product.setCpu(newProduct.getCpu());
            product.setRam(newProduct.getRam());
            product.setOs(newProduct.getOs());
            product.setBatteryCapacity(newProduct.getBatteryCapacity());
            product.setScreenSize(newProduct.getScreenSize());

            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating product" + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<Object> searchProducts(String keyword, Pageable pageable) {
        try {
            Page<Products> product = productRepository.findByProductNameContainingOrModelContaining(keyword, keyword, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "products", product,
                    "totalPages", product.getTotalPages(),
                    "totalElements", product.getTotalElements()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @Override
    public void delete(Long productId) {
        try{
            Products product = productRepository.findByProductId(productId);
            if (product == null) throw new RuntimeException("Product not found");
            // Xoá hình ảnh từ thư mục static/images
            String fileName = product.getFileName();
            if (fileName != null && !fileName.equals("/images/default.jpg")) {
                Path imagePath = Paths.get(UPLOAD_DIRECTORY, fileName.substring(storeImageUrl.length()));
                Files.deleteIfExists(imagePath);
            }
            productRepository.delete(product);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public ResponseEntity<Resource> getImage(String imageName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIRECTORY).resolve(imageName).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private Products findSimilarProduct(NewProduct product) {
        // Thực hiện truy vấn để tìm sản phẩm có các trường tương tự
        return productRepository.findByProductNameAndModelAndColorAndStorageCapacity(
                product.getProductName(),
                product.getModel(),
                product.getColor(),
                product.getStorageCapacity()
        );
    }
    private String generateRandomFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

    private String storeImage(MultipartFile file) throws IOException {
        String randomFileName = generateRandomFileName(file);
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, randomFileName);
        Files.write(fileNameAndPath, file.getBytes());
        return storeImageUrl + randomFileName;
    }
}
