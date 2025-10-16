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
                              @RequestParam(defaultValue = "0") int page,
                              Model model) {

        int pageSize = 12;

        // Filtra los productos activos según filtros
        List<Product> filteredProducts = productService.getAllProductsOrdered().stream()
                .filter(Product::isStatus)
                .filter(p -> keyword == null || keyword.isEmpty() ||
                        p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .filter(p -> category == null || category.isEmpty() ||
                        p.getCategory().equalsIgnoreCase(category))
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

        // obtiene las categorias
        List<String> categories = productService.getAllProductsOrdered().stream()
                .map(Product::getCategory)
                .filter(c -> c != null && !c.isEmpty())
                .distinct()
                .toList();


        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("products", pageProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "catalog/catalog";
    }

}
