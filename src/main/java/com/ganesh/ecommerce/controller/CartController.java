package com.ganesh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.model.Cart;
import com.ganesh.ecommerce.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add to cart
    @PostMapping
    public Cart addToCart(@RequestBody Cart cart) {
        return cartService.addToCart(cart);
    }

    // Get cart by user
    @GetMapping("/{userId}")
    public List<Cart> getCart(@PathVariable int userId) {
        return cartService.getCartByUser(userId);
    }

    // Delete item
    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable int id) {
        cartService.deleteCartItem(id);
        return "Item removed";
    }
}