/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.motollantas.MotoLlantasVirtual.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author esteb
 */
@Controller
public class HomeController {

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/catalog")
    public String catalog() {
        return "catalog/catalog";
    }

    @GetMapping("/productDetails")
    public String productDetails() {
        return "catalog/productDetails";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart/cart";
    }

    @GetMapping("/inventory")
    public String inventory() {
        return "inventory/inventory";
    }

    @GetMapping("/location")
    public String location() {
        return "location";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/register")
    public String register() {
        return "login/register";
    }

    @GetMapping("/users")
    public String users() {
        return "users/adminDashboard";
    }

    @GetMapping("/contabilidad")
    public String contabilidad() {
        return "contabilidad/contabilidad";
    }

    @GetMapping("/userGarage")
    public String garageUser() {
        return "garage/userGarage";
    }

    @GetMapping("/marketing")
    public String marketing() {
        return "marketing/marketing";
    }

    @GetMapping("/adminGarage")
    public String garageAdmin() {
        return "garage/adminGarage";
    }

}
