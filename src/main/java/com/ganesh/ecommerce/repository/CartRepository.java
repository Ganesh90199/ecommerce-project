package com.ganesh.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ganesh.ecommerce.model.Cart;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {


    List<Cart> findByUserId(int userId);

    Cart findByUserIdAndProductId(int userId, int productId);
}