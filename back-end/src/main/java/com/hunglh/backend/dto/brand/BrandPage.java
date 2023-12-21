package com.hunglh.backend.dto.brand;

import com.hunglh.backend.entities.Products;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class BrandPage {
    private String brandName;
    private Page<Products> page;

    public BrandPage(String brandName, Page<Products> page) {
        this.brandName = brandName;
        this.page = page;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setPage(Page<Products> page) {
        this.page = page;
    }
}
