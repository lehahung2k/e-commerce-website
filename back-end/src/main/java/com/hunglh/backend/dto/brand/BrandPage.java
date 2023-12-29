package com.hunglh.backend.dto.brand;

import com.hunglh.backend.entities.Products;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Getter
public class BrandPage {
    private String brandName;
    private ResponseEntity<Object> page;

    public BrandPage(String brandName, ResponseEntity<Object> page) {
        this.brandName = brandName;
        this.page = page;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setPage(ResponseEntity<Object> page) {
        this.page = page;
    }
}
