package com.hunglh.backend.services.impl;

import com.hunglh.backend.entities.ProductBrand;
import com.hunglh.backend.repositories.ProductBrandRepository;
import com.hunglh.backend.services.ProductBrandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductBrandServiceImpl implements ProductBrandService {

    private final ProductBrandRepository productBrandRepository;

    @Override
    public List<ProductBrand> findAll() {
        try {
            List<ProductBrand> res = productBrandRepository.findAllByOrderByBrandType();
            return res;
        } catch (Exception e) {
            throw new EntityNotFoundException("Brand type not found");
        }
    }

    @Override
    public ProductBrand findByBrandType(Integer brandType) {
        ProductBrand res = productBrandRepository.findByBrandType(brandType);
        if(res == null) throw new EntityNotFoundException("Brand type not found");
        return res;
    }

    @Override
    public List<ProductBrand> findByBrandTypeIn(List<Integer> brandTypes) {
        try {
            List<ProductBrand> res = productBrandRepository.findByBrandTypeInOrderByBrandTypeAsc(brandTypes);
            //res.sort(Comparator.comparing(ProductCategory::getCategoryType));
            return res;
        } catch (Exception e) {
            throw new EntityNotFoundException("Brand type not found");
        }
    }

    @Override
    public ProductBrand save(ProductBrand productBrand) {
        return productBrandRepository.save(productBrand);
    }
}
