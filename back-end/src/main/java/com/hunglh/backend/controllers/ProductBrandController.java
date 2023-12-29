package com.hunglh.backend.controllers;

import com.hunglh.backend.dto.brand.BrandPage;
import com.hunglh.backend.entities.ProductBrand;
import com.hunglh.backend.entities.Products;
import com.hunglh.backend.services.ProductBrandService;
import com.hunglh.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProductBrandController {

    private final ProductBrandService brandService;
    private final ProductService productService;

    /**
     * Show products in category
     *
     * @param categoryType
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/category/{type}")
    public BrandPage showOne(@PathVariable("type") Integer brandType,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "3") Integer size) {

        ProductBrand brand = brandService.findByBrandType(brandType);
        PageRequest request = PageRequest.of(page - 1, size);
        ResponseEntity<Object> productInBrand = productService.findAllInBrand(brandType, request);
        var tmp = new BrandPage("", productInBrand);
        tmp.setBrandName(brand.getBrandName());
        return tmp;
    }
}
