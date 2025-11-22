/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author esteb
 */

@Entity
@Table(name = "document_type")
public class DocumentType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String code;  // DIMEX, PASSPORT, CJURIDICA, CFISICA

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "pattern_regex", length = 200)
    private String patternRegex;
    @Column(name = "min_length") private Integer minLength;
    @Column(name = "max_length") private Integer maxLength;

    @Column(name = "is_active", nullable = false) private Boolean isActive = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatternRegex() {
        return patternRegex;
    }

    public void setPatternRegex(String patternRegex) {
        this.patternRegex = patternRegex;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public DocumentType() {
    }

    public DocumentType(Integer id, String code, String name, String patternRegex, Integer minLength, Integer maxLength) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.patternRegex = patternRegex;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
    
    
}

