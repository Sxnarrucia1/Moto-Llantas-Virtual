package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.CategoryService;
import com.motollantas.MotoLlantasVirtual.Service.ProductService;
import com.motollantas.MotoLlantasVirtual.domain.Category;
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

    @Autowired
    private CategoryService categoryService;

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
                              @RequestParam(required = false) Long category,
                              @RequestParam(defaultValue = "0") int page,
                              Model model) {

        int pageSize = 12;

        // Obtener todos los productos activos
        List<Product> activeProducts = productService.getAllProductsOrdered().stream()
                .filter(Product::isStatus) // Solo productos activos
                .toList();

        // Aplicar filtros dinámicos
        List<Product> filteredProducts = activeProducts.stream()
                .filter(p -> keyword == null || keyword.isBlank() ||
                        p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .filter(p -> category == null ||
                        (p.getCategory() != null && p.getCategory().getId().equals(category)))
                .toList();


        // calculando la cantidad de paginas
        int totalProducts = filteredProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // Evita errores si la página pedida está fuera de rango
        if (page < 0) page = 0;
        if (page >= totalPages && totalPages > 0) page = totalPages - 1;

        // Calcula los índices de sublista correctamente
        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalProducts);

        // solo toma los productos de la página actual
        List<Product> pageProducts = filteredProducts.subList(start, end);

        List<Category> categories = categoryService.getAllCategories();


        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("products", pageProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "catalog/catalog";
    }

}
