package com.hunglh.backend.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class UpdateProduct {

    private Long productId;
    private String productName;
    private Integer brand;
    private String model;
    private BigDecimal price;
    private String description;
    private String fileName;
    private Integer quantityInStock;
    private String color;
    private String storageCapacity;
    private String screenSize;
    private String ram;
    private String cpu;
    private String os;
    private String batteryCapacity;
    private Integer productStatus;
}
