package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_description")
    private String description;

    private String category;

    private BigDecimal price;

    @Column(name = "stock_quantity")
    private Integer stock;

    private boolean status;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

}
