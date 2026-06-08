package com.ganesh.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ganesh.ecommerce.config.JwtUtil;
import com.ganesh.ecommerce.model.Cart;
import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.repository.UserRepository;
import com.ganesh.ecommerce.service.CartService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // Add to Cart
    @PostMapping
    public Cart addToCart(@RequestBody Cart cart,
                          HttpServletRequest request) {

        String token =
                request.getHeader("Authorization")
                       .substring(7);

        String email = jwtUtil.extractEmail(token);

        User user =
                userRepository.findByEmail(email).get();

        cart.setUserId(user.getId());

        return cartService.addToCart(cart);
    }

    // View My Cart
    @GetMapping("/my")
    public List<Cart> getMyCart(HttpServletRequest request) {

        String token =
                request.getHeader("Authorization")
                       .substring(7);

        String email = jwtUtil.extractEmail(token);

        User user =
                userRepository.findByEmail(email).get();

        return cartService.getCartByUser(user.getId());
    }

    // Remove Item
    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable int id) {

        cartService.deleteCartItem(id);

        return "Item removed";
    }
}