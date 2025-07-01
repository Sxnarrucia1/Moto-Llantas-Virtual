package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.ProductService;
import com.motollantas.MotoLlantasVirtual.dao.ProductDao;
import com.motollantas.MotoLlantasVirtual.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

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
        return productDao.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<Product> getAllProductsOrdered() {
        return productDao.findAllByOrderByStatusDescNameAsc();
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }


}
