package com.hunglh.backend.services;

import com.hunglh.backend.entities.ProductBrand;
import java.util.List;

public interface ProductBrandService {

    List<ProductBrand> findAll();

    ProductBrand findByBrandType(Integer brandType);

    List<ProductBrand> findByBrandTypeIn(List<Integer> brandTypes);

    ProductBrand save(ProductBrand productBrand);
}
