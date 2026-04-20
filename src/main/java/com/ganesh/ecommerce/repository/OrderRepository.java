package com.ganesh.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ganesh.ecommerce.model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(int userId);
}  