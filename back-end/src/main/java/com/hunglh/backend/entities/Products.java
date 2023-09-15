package com.hunglh.backend.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", initialValue = 109, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description")
    private String description;

    @Column(name = "filename")
    private String filename;

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

    @Column(name = "rating")
    private Double rating;

    @OneToMany
    @ToString.Exclude
    private List<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Objects.equals(id, products.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
