package com.ganesh.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.model.Order;
import com.ganesh.ecommerce.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(Order order) {

        // ✅ Validate input (PREVENT 500 ERROR)
        if (order == null) {
            throw new RuntimeException("Order is null");
        }

        if (order.getUserId() == 0) {
            throw new RuntimeException("UserId is required");
        }

        if (order.getTotalAmount() <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        order.setStatus("PLACED");

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(int userId) {
        return orderRepository.findByUserId(userId);
    }
}