package com.hunglh.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
public class Products {
    public Products() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brand")
    private Integer brand;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private BigDecimal price;

    @Lob
    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @Column(name = "color")
    private String color;

    @Column(name = "storage_capacity")
    private String storageCapacity;

    @Column(name = "screen_size")
    private String screenSize;

    @Column(name = "ram")
    private String ram;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "os")
    private String os;

    @Column(name = "battery_capacity")
    private String batteryCapacity;

    @ColumnDefault("0")
    private Integer productStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Objects.equals(productId, products.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
