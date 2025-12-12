package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private BigDecimal price;

    @Column(name = "stock_quantity")
    private Integer stock;

    private boolean status;

    @Column(name = "lote")
    private String lote;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category  category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public Product() {
    }

    public Product(String name, String description, Category  category, BigDecimal price, Integer stock, boolean status, LocalDate expirationDate, String imageUrl, String lote) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.expirationDate = expirationDate;
        this.imageUrl = imageUrl;
        this.lote = lote;
    }
}
