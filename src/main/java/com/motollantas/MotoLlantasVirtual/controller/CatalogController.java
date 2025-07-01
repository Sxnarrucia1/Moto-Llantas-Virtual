package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.ProductService;
import com.motollantas.MotoLlantasVirtual.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private ProductService productService;

    @GetMapping("/catalog/productDetails/{id}")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);

        if (product == null || !product.isStatus()) {
            return "redirect:/catalog"; // Redirige si el producto no existe o está inactivo
        }

        model.addAttribute("product", product);
        return "catalog/productDetails";
    }
    @GetMapping("/catalog")
    public String showCatalog(Model model) {
    // Imprime en consola los productos activos
    System.out.println(productService.getActiveProducts());

    model.addAttribute("products", productService.getActiveProducts());
    return "catalog";
}

}
