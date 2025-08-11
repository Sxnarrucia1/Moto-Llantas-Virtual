package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.ProductService;
import com.motollantas.MotoLlantasVirtual.Service.S3Service;
import com.motollantas.MotoLlantasVirtual.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/inventory")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private S3Service s3Service;

    @GetMapping
    public String listInventory(Model model) {
        List<Product> products = productService.getAllProductsOrdered();
        List<Product> expiringProducts = productService.getProductsExpiringSoon(30);

        model.addAttribute("productos", products);
        model.addAttribute("expiringProducts", expiringProducts);
        System.out.println("Productos por vencer: " + expiringProducts.size());
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
        model.addAttribute("productDetail", product);
        model.addAttribute("productos", productService.getAllProductsOrdered());
        return "inventory/inventory";
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

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            String imageUrl = s3Service.uploadFile(imageFile); // Subimos a S3
            product.setImageUrl(imageUrl); // Guardamos la URL en el producto
        }
        productService.save(product);
        return "redirect:/inventory";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product;
        if (id == 0) {
            product = new Product(); // Nuevo producto vacío para crear
        } else {
            product = productService.getProductById(id); // Producto existente para editar
        }
        model.addAttribute("editProduct", product);
        model.addAttribute("productos", productService.getAllProductsOrdered());
        return "inventory/inventory";
    }

}
