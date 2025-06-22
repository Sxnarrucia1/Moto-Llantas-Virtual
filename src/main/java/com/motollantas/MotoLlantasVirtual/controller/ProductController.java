package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.ProductService;
import com.motollantas.MotoLlantasVirtual.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public String listInventory(Model model) {
        List<Product> products = productService.getAllProductsOrdered();
        model.addAttribute("productos", products);
        return "inventory/inventory";
    }


    @PostMapping("/reactivate/{id}")
    public String reactiveProducts(@PathVariable Long id) {
        productService.reactivateProduct(id);
        return "redirect:/inventory";
    }

    @PostMapping("/deactivate/{id}")
    public String deactivateProduct(@PathVariable Long id) {
        productService.deactivateProductById(id);
        return "redirect:/inventory";
    }



    @GetMapping("/detail/{id}")
    public String viewProductDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "inventory";
    }


    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/inventory";
        }
        List<Product> filterProducts = productService.searchForName(keyword);
        model.addAttribute("productos", filterProducts);
        return "inventory/inventory";
    }

}
