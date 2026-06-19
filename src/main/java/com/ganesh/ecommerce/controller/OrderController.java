package com.ganesh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.ganesh.ecommerce.dto.DashboardResponseDTO;
import com.ganesh.ecommerce.dto.OrderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.config.JwtUtil;
import com.ganesh.ecommerce.dto.PlaceOrderRequest;
import com.ganesh.ecommerce.model.Order;
import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.repository.UserRepository;
import com.ganesh.ecommerce.service.OrderService;
import com.ganesh.ecommerce.dto.OrderStatusCountDTO;
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

    @PostMapping
    public ResponseEntity<?> placeOrder(
            @RequestBody PlaceOrderRequest requestBody,
            HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        String token = header.substring(7);

        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();

        order.setUserId(user.getId());
        order.setTotalAmount(requestBody.getTotalAmount());
        order.setStatus("PLACED");
        
        order.setCustomerName(
                requestBody.getCustomerName()
        );

        order.setMobile(
                requestBody.getMobile()
        );

        order.setAddress(
                requestBody.getAddress()
        );

        order.setCity(
                requestBody.getCity()
        );

        order.setPincode(
                requestBody.getPincode()
        );

        Order savedOrder =
                orderService.placeOrder(order);

        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyOrders(
            HttpServletRequest request) {

        String header = request.getHeader("Authorization");
        String token = header.substring(7);

        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderResponseDTO> orders =
                orderService.getOrdersByUser(
                        user.getId()
                );

        return ResponseEntity.ok(orders);
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(
            @PathVariable int id) {

        return ResponseEntity.ok(
                orderService.cancelOrder(id)
        );
    }
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllOrders() {

        System.out.println(
                "ADMIN ALL ORDERS API HIT"
        );

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }

    @PutMapping("/admin/status/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable int id,
            @RequestBody java.util.Map<String, String> body) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(
                        id,
                        body.get("status")
                )
        );
    }
    @GetMapping("/admin/dashboard")
    public ResponseEntity<DashboardResponseDTO>
            getDashboard() {

        return ResponseEntity.ok(
                orderService.getDashboardData()
        );
    }
    @GetMapping("/admin/order-status")
    public ResponseEntity<
            List<OrderStatusCountDTO>>
            getOrderStatusCounts() {

        return ResponseEntity.ok(
                orderService
                .getOrderStatusCounts()
        );
    }
}