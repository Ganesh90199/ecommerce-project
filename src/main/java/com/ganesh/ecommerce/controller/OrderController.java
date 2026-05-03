package com.ganesh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.model.Order;
import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.repository.UserRepository;
import com.ganesh.ecommerce.service.OrderService;
import com.ganesh.ecommerce.config.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // ✅ Place Order (userId from token)
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order, HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        String token = header.substring(7);

        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email).get(0);

        order.setUserId(user.getId());

        Order savedOrder = orderService.placeOrder(order);

        return ResponseEntity.ok(savedOrder);
    }

    // ✅ Get logged-in user's orders ONLY
    @GetMapping("/my")
    public ResponseEntity<?> getMyOrders(HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        String token = header.substring(7);

        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email).get(0);

        List<Order> orders = orderService.getOrdersByUser(user.getId());

        return ResponseEntity.ok(orders);
    }
}