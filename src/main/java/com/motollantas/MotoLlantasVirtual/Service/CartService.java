package com.motollantas.MotoLlantasVirtual.Service;

import com.motollantas.MotoLlantasVirtual.domain.Cart;
import com.motollantas.MotoLlantasVirtual.domain.User;

import java.util.List;

public interface CartService {
    void addProduct(User user, Long productId, int quantity);
    void updateQuantity(User user, Long productId, int quantity);
    void removeProduct(User user, Long productId);
    List<Cart> getUserCart(User user);

}
