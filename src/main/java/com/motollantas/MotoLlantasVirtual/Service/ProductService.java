package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getActiveProducts();

    List<Product> getInactiveProducts();

    Product getProductById(Long id);

    void reactivateProduct(Long id);

    void deactivateProductById(Long id);

    List<Product> getAllProductsOrdered();

    List<Product> searchForName(String keyword);

    void save(Product product);

    String saveProduct(Product product, MultipartFile imageFile);

    public List<Product> getProductsExpiringSoon(int days);

}
