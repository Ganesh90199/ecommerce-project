package com.ganesh.ecommerce.controller;

import java.util.List;
import com.ganesh.ecommerce.dto.CartResponseDTO;

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
import java.util.Map;
import org.springframework.web.bind.annotation.PutMapping;

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
    @GetMapping("/details")
    public List<CartResponseDTO> getCartDetails(
            HttpServletRequest request) {

        String token =
                request.getHeader("Authorization")
                       .substring(7);

        String email =
                jwtUtil.extractEmail(token);

        User user =
                userRepository.findByEmail(email)
                              .get();

        return cartService.getCartDetails(
                user.getId()
        );
    }
    

    // Remove Item
    @DeleteMapping("/item/{id}")
    public String deleteCart(@PathVariable int id) {

        cartService.deleteCartItem(id);

        return "Item removed";
    }
    @PutMapping("/{id}")
    public Cart updateQuantity(
            @PathVariable int id,
            @RequestBody Map<String, Integer> body) {

        return cartService.updateQuantity(
                id,
                body.get("quantity")
        );
    }
    @DeleteMapping("/clear")
    public String clearCart(
            HttpServletRequest request) {

        String token =
                request.getHeader("Authorization")
                       .substring(7);

        String email =
                jwtUtil.extractEmail(token);

        User user =
                userRepository.findByEmail(email)
                              .get();

        cartService.clearCart(user.getId());

        return "Cart cleared";
    }    
    
}