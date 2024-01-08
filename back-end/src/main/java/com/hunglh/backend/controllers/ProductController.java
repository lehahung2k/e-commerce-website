package com.hunglh.backend.controllers;

import com.hunglh.backend.dto.product.NewProduct;
import com.hunglh.backend.entities.Products;
import com.hunglh.backend.services.ImageService;
import com.hunglh.backend.services.ProductBrandService;
import com.hunglh.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductBrandService productBrandService;
    private final ProductService productService;
    private final ImageService imageService;

    @GetMapping("/product")
    public ResponseEntity<Object> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "8") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.findAll(request);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Object> productDetail(@PathVariable("productId") Long productId) {
        return productService.findOne(productId);
    }

    @PostMapping("/seller/product/new")
    public ResponseEntity<Products> addNewProduct(@Valid @ModelAttribute NewProduct product,
                                 BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping("/seller/product/{productId}/edit")
    public ResponseEntity<Object> editProduct(@PathVariable("productId") Long productId,
                               @Valid @RequestBody Products product,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if (!productId.equals(product.getProductId())) {
            return ResponseEntity.badRequest().body("Id Not Matched");
        }

        return ResponseEntity.ok(Map.of("message", productService.update(product)));
    }

    @GetMapping("/product/search")
    public ResponseEntity<Object> searchProducts(
            @RequestParam String keyword,
            @PageableDefault(size = 8, sort = "productId") Pageable pageable) {
        return productService.searchProducts(keyword, pageable);
    }

    @DeleteMapping("/seller/product/{productId}/delete")
    public ResponseEntity<Object> delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/upload/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
        return imageService.getImage(fileName);
    }

}
