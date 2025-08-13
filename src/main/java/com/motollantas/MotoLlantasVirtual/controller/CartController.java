package com.motollantas.MotoLlantasVirtual.controller;

import com.motollantas.MotoLlantasVirtual.Service.CartService;
import com.motollantas.MotoLlantasVirtual.dao.UserDao;
import com.motollantas.MotoLlantasVirtual.domain.Cart;
import com.motollantas.MotoLlantasVirtual.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserDao userDao;

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();


        if (email == null || email.equals("anonymousUser")) {
            return null;
        }

        return userDao.findByEmail(email).orElse(null);
    }

    @GetMapping
    public String viewCart(Model model) {
        User user = getAuthenticatedUser();
        List<Cart> cartItems = cartService.getUserCart(user);

        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("cartItems", cartItems);

        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        User user = getAuthenticatedUser();
        cartService.addProduct(user, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        User user = getAuthenticatedUser();
        cartService.updateQuantity(user, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeProduct(@RequestParam Long productId) {
        User user = getAuthenticatedUser();
        cartService.removeProduct(user, productId);
        return "redirect:/cart";
    }
}
