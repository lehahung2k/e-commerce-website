package com.hunglh.backend.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String productName;
    private String brand;
    private String model;
    private Integer price;
    private String description;
    private String filename;
    private Integer quantityInStock;
    private String color;
    private String storageCapacity;
    private String screenSize;
    private String ram;
    private String cpu;
    private String os;
    private String batteryCapacity;
    private Double rating;
    private Integer reviewsCount;
}
