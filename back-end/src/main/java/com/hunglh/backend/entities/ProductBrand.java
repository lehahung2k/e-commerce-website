package com.hunglh.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.util.Date;

@Entity
@Data
@Table(name = "product_brand")
public class ProductBrand {
    @Id
    @GeneratedValue
    @Column(name = "brandId")
    private Integer brandId;

    @Column(name = "brand_name")
    private String brandName;

    @NaturalId
    @Column(name = "brand_type")
    private Integer brandType;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public ProductBrand() {
    }

    public ProductBrand(String brandName, Integer brandType) {
        this.brandName = brandName;
        this.brandType = brandType;
    }
}
