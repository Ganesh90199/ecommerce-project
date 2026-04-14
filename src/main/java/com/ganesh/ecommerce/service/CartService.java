package com.ganesh.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.model.Cart;
import com.ganesh.ecommerce.repository.CartRepository;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart addToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Cart> getCartByUser(int userId) {
        return cartRepository.findByUserId(userId);
    }

    public void deleteCartItem(int id) {
        cartRepository.deleteById(id);
    }
}