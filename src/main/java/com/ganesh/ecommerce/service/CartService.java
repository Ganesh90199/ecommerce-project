package com.ganesh.ecommerce.service;

import java.util.List;
import java.util.ArrayList;
import com.ganesh.ecommerce.dto.CartResponseDTO;
import com.ganesh.ecommerce.model.Product;
import com.ganesh.ecommerce.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.model.Cart;
import com.ganesh.ecommerce.repository.CartRepository;



@Service
public class CartService {

	@Autowired
	private ProductRepository productRepository;
	
    @Autowired
    private CartRepository cartRepository;

    public Cart addToCart(Cart cart) {

        Cart existing =
                cartRepository.findByUserIdAndProductId(
                        cart.getUserId(),
                        cart.getProductId()
                );

        if (existing != null) {

            existing.setQuantity(
                    existing.getQuantity()
                            + cart.getQuantity()
            );

            return cartRepository.save(existing);
        }

        return cartRepository.save(cart);
    }

    public List<Cart> getCartByUser(int userId) {

        return cartRepository.findByUserId(userId);
    }

    public void deleteCartItem(int id) {

        cartRepository.deleteById(id);
    }
    public void clearCart(int userId) {

        System.out.println("CLEARING USER = " + userId);

        List<Cart> cartItems =
                cartRepository.findByUserId(userId);

        System.out.println("FOUND ITEMS = " + cartItems.size());

        cartRepository.deleteAll(cartItems);

        System.out.println("DELETED");
    }
    public List<CartResponseDTO> getCartDetails(int userId) {

        List<Cart> cartItems =
                cartRepository.findByUserId(userId);

        List<CartResponseDTO> response =
                new ArrayList<>();

        for (Cart cart : cartItems) {

            Product product =
                    productRepository.findById(
                            cart.getProductId()
                    ).orElse(null);

            if (product == null) {
                continue;
            }

            CartResponseDTO dto =
                    new CartResponseDTO();

            dto.setCartId(cart.getId());
            dto.setProductId(product.getId());

            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setImageUrl(product.getImageUrl());

            dto.setStock(product.getQuantity());
            dto.setCartQuantity(cart.getQuantity());

            response.add(dto);
        }

        return response;
    }
    
    public Cart updateQuantity(
            int cartId,
            int quantity) {

        Cart cart =
                cartRepository.findById(cartId)
                        .orElseThrow(
                            () -> new RuntimeException(
                                "Cart item not found"
                            )
                        );

        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }
}