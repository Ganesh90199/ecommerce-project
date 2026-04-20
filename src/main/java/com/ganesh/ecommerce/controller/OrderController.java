package com.ganesh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.model.Order;
import com.ganesh.ecommerce.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ✅ Place order
    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    // ✅ Get orders by user
    @GetMapping("/{userId}")
    public List<Order> getOrders(@PathVariable int userId) {
        return orderService.getOrdersByUser(userId);
    }
}