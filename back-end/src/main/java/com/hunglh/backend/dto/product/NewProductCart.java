package com.hunglh.backend.dto.product;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class NewProductCart {
    @Min(value = 1)
    private Integer quantityInStock;
    @NotEmpty
    private Long productId;
}
