package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.CategoryService;
import com.motollantas.MotoLlantasVirtual.Service.ProductService;
import com.motollantas.MotoLlantasVirtual.Service.S3Service;
import com.motollantas.MotoLlantasVirtual.dao.ProductDao;
import com.motollantas.MotoLlantasVirtual.domain.Category;
import com.motollantas.MotoLlantasVirtual.domain.Product;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<Product> getActiveProducts() {
        return productDao.findByStatusTrue();
    }

    @Override
    public List<Product> getInactiveProducts() {
        return productDao.findByStatusFalse();
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public void reactivateProduct(Long id) {
        productDao.findById(id).ifPresent(p -> {
            p.setStatus(true);
            productDao.save(p);
        });
    }

    @Override
    public void deactivateProductById(Long id) {
        Product p = productDao.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        p.setStatus(false);
        productDao.save(p);
    }

    @Override
    public List<Product> searchForName(String keyword) {
        return productDao.findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<Product> getAllProductsOrdered() {
        return productDao.findAllByOrderByStatusDescNameAsc();
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public String saveProduct(Product product, MultipartFile imageFile) {

        // Validación de precio
        if (product.getPrice() != null && product.getPrice().doubleValue() > 99999999.99) {
            return "error: El precio excede el valor permitido.";
        }

        // Validación de stock
        if (product.getStock() != null && product.getStock() > 1000000000) {
            return "error: El stock es demasiado grande.";
        }

        Product original = null;

        if (product.getId() != null) {
            original = productDao.findById(product.getId())
                    .orElse(null);
        }

        // Manejo de imagen con S3
        try {
            if (!imageFile.isEmpty()) {
                String imageUrl = s3Service.uploadFile(imageFile);
                product.setImageUrl(imageUrl);
            } else if (original != null) {
                product.setImageUrl(original.getImageUrl());
            }
        } catch (Exception e) {
            return "error: No se pudo procesar la imagen.";
        }

        // Manejo de fecha
        if (product.getExpirationDate() == null && original != null) {
            product.setExpirationDate(original.getExpirationDate());
        }

        // Maneja la categoria
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category cat = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(cat);
        } else if (original != null) {
            product.setCategory(original.getCategory());
        }



        System.out.println("CATEGORY ID RECIBIDO = " + product.getCategory().getId());

        // Guardar en base
        productDao.save(product);
        return "success";
    }


    @Override
    public List<Product> getProductsExpiringSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate threshold = today.plusDays(days);
        return productDao.findByExpirationDateBetween(today, threshold);
    }

}
