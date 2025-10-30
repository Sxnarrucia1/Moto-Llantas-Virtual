package com.motollantas.MotoLlantasVirtual.ServiceImpl;

import com.motollantas.MotoLlantasVirtual.Service.CartService;
import com.motollantas.MotoLlantasVirtual.dao.CartDao;
import com.motollantas.MotoLlantasVirtual.dao.ProductDao;
import com.motollantas.MotoLlantasVirtual.domain.Cart;
import com.motollantas.MotoLlantasVirtual.domain.Product;
import com.motollantas.MotoLlantasVirtual.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public void addProduct(User user, Long productId, int quantity) {
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("No puedes agregar más de "
                    + product.getStock() + " unidades del producto '"
                    + product.getName() + "'.");
        }

        Cart cartItem = cartDao.findByUserAndProduct(user, product)
                .orElse(new Cart());

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItem.getCartId() == null
                ? quantity
                : cartItem.getQuantity() + quantity);

        cartDao.save(cartItem);
    }


    @Override
    public void updateQuantity(User user, Long productId, int quantity) {
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (quantity > product.getStock()) {
            throw new RuntimeException("Cantidad supera el stock disponible");
        }

        Cart cartItem = cartDao.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("Producto no está en el carrito"));

        cartItem.setQuantity(quantity);
        cartDao.save(cartItem);
    }

    @Override
    @Transactional
    public void removeProduct(User user, Long productId) {
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        cartDao.deleteByUserAndProduct(user, product);
    }

    @Override
    public List<Cart> getUserCart(User user) {
        return cartDao.findByUser(user);
    }
}
