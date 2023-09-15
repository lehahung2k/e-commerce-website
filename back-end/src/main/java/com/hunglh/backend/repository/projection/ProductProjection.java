package com.hunglh.backend.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ProductProjection {
    Long getId();
    String getProductName();
    String getBrand();
    String getModel();
    Integer getPrice();
    String getFilename();
    Integer getQuantityInStock();
    String getColor();
    String getStorageCapacity();
    Double getRating();
    
    @Value("#{target.reviews.size()}")
    Integer getReviewsCount();

    void setProductName(String productName);
    void setPrice(Integer price);
    void setQuantityInStock(Integer quantityInStock);
}
