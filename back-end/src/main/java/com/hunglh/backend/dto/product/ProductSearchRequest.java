package com.hunglh.backend.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchRequest {
    private List<String> brand;
    private List<String> model;
    private List<String> storageCapacity;
    private List<String> ramCapacity;
    private List<Integer> prices;
    private Boolean sortByPrice;
    private String brandName;
    private String modelName;
}
