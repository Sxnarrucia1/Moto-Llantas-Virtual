package com.motollantas.MotoLlantasVirtual.Service;


import com.motollantas.MotoLlantasVirtual.domain.Product;

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


}
