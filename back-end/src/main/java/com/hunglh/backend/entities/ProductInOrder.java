package com.hunglh.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@ToString
@Table(name = "product_in_order")
@NoArgsConstructor
public class ProductInOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sử dụng IDENTITY cho MySQL
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderMain orderMain;

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

    @Column(name = "count")
    private Integer count;

    public ProductInOrder(Products products, Integer quantity) {
        this.productId = products.getProductId();
        this.productName = products.getProductName();
        this.description = products.getDescription();
        this.fileName = products.getFileName();
        this.brand = products.getBrand();
        this.model = products.getModel();
        this.color = products.getColor();
        this.storageCapacity = products.getStorageCapacity();
        this.screenSize = products.getScreenSize();
        this.ram = products.getRam();
        this.cpu = products.getCpu();
        this.os = products.getOs();
        this.batteryCapacity = products.getBatteryCapacity();
        this.price = products.getPrice();
        this.quantityInStock = products.getQuantityInStock();
        this.count = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductInOrder that = (ProductInOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(model, that.model) &&
                Objects.equals(color, that.color) &&
                Objects.equals(storageCapacity, that.storageCapacity) &&
                Objects.equals(screenSize, that.screenSize) &&
                Objects.equals(ram, that.ram) &&
                Objects.equals(cpu, that.cpu) &&
                Objects.equals(os, that.os) &&
                Objects.equals(batteryCapacity, that.batteryCapacity) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, productId, productName, description, fileName, brand, model, color, storageCapacity, screenSize, ram, cpu, os, batteryCapacity, price);
    }
}
