/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author esteb
 */
@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long idUser;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "identification")
    private String identification;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    private String userType;

    public User() {
    }

    public User(String fullName, String identification, String email, String password, String userType) {
        this.fullName = fullName;
        this.identification = identification;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
}