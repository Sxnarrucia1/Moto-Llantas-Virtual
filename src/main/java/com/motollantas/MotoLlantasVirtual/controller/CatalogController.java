package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.ProductService;
import com.motollantas.MotoLlantasVirtual.domain.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productDetails/{id}")
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
    System.out.println(productService.getActiveProducts());
    model.addAttribute("products", productService.getActiveProducts());
    return "catalog/catalog";
}
    
    @GetMapping("/search")
    public String searchCatalog(@RequestParam(required = false) String keyword, Model model) {
        List<Product> filteredProducts = productService.getActiveProducts().stream()
                .filter(p -> keyword == null || keyword.isBlank()
                        || p.getName().toLowerCase().contains(keyword.toLowerCase())
                        || p.getCategory().toLowerCase().contains(keyword.toLowerCase()))
                .toList();

        model.addAttribute("products", filteredProducts);
        model.addAttribute("keyword", keyword); // para mantener el valor en el input
        return "catalog/catalog";
    }
}
