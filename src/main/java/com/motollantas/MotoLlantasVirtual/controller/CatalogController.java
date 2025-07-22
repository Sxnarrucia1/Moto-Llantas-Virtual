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
            return "redirect:/catalog";
        }

        model.addAttribute("product", product);
        return "catalog/productDetails";
    }

    @GetMapping("/catalog")
    public String showCatalog(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String category,
                               Model model) {

        List<Product> filteredProducts = productService.getAllProductsOrdered().stream()
            .filter(p -> p.isStatus()) // solo productos activos
            .filter(p -> keyword == null || keyword.isEmpty() ||
                         p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                         p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
            .filter(p -> category == null || category.isEmpty() ||
                         p.getCategory().equalsIgnoreCase(category))
            .toList();

        List<String> categories = productService.getAllProductsOrdered().stream()
            .map(Product::getCategory)
            .filter(c -> c != null && !c.isEmpty())
            .distinct()
            .toList();

        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("products", filteredProducts);

        return "catalog/catalog";
    }
}
