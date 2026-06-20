package com.ganesh.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.dto.DashboardResponseDTO;
import com.ganesh.ecommerce.dto.OrderItemDTO;
import com.ganesh.ecommerce.dto.OrderResponseDTO;
import com.ganesh.ecommerce.dto.OrderStatusCountDTO;
import com.ganesh.ecommerce.model.Cart;
import com.ganesh.ecommerce.model.Order;
import com.ganesh.ecommerce.model.OrderItem;
import com.ganesh.ecommerce.model.Product;
import com.ganesh.ecommerce.repository.CartRepository;
import com.ganesh.ecommerce.repository.OrderItemRepository;
import com.ganesh.ecommerce.repository.OrderRepository;
import com.ganesh.ecommerce.repository.ProductRepository;
import com.ganesh.ecommerce.repository.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Order placeOrder(Order order) {

        order.setStatus("PLACED");
        order.setOrderDate(
                LocalDateTime.now().toString()
        );

        Order savedOrder =
                orderRepository.save(order);

        List<Cart> cartItems =
                cartRepository.findByUserId(
                        order.getUserId()
                );

        double totalAmount = 0;

        for (Cart cart : cartItems) {

        	System.out.println(
        	        "Cart Product ID = "
        	        + cart.getProductId()
        	);

        	Product product =
        		    productRepository.findById(
        		        cart.getProductId()
        		    ).orElse(null);

        		if(product == null){

        		    cartRepository.delete(cart);

        		    continue;
        		}
            if (
                    product.getQuantity()
                    < cart.getQuantity()
            ) {
                throw new RuntimeException(
                        product.getName()
                                + " is out of stock"
                );
            }

            OrderItem item =
                    new OrderItem();

            item.setOrderId(
                    savedOrder.getId()
            );

            item.setProductId(
                    product.getId()
            );

            item.setProductName(
                    product.getName()
            );

            item.setQuantity(
                    cart.getQuantity()
            );

            item.setPrice(
                    product.getPrice()
            );

            orderItemRepository.save(item);

            product.setQuantity(
                    product.getQuantity()
                            - cart.getQuantity()
            );

            productRepository.save(product);

            totalAmount +=
                    product.getPrice()
                            * cart.getQuantity();
        }

        savedOrder.setTotalAmount(
                totalAmount
        );

        savedOrder =
                orderRepository.save(savedOrder);

        cartRepository.deleteAll(
                cartItems
        );

        return savedOrder;
    }

    public List<OrderResponseDTO> getOrdersByUser(
            int userId) {

        List<Order> orders =
                orderRepository.findByUserIdOrderByIdDesc(
                        userId
                );

        List<OrderResponseDTO> response =
                new ArrayList<>();

        for (Order order : orders) {

            OrderResponseDTO dto =
                    new OrderResponseDTO();

            dto.setId(order.getId());
            dto.setStatus(order.getStatus());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setOrderDate(order.getOrderDate());

            dto.setCustomerName(
                    order.getCustomerName()
            );

            dto.setMobile(
                    order.getMobile()
            );

            dto.setAddress(
                    order.getAddress()
            );

            dto.setCity(
                    order.getCity()
            );

            dto.setPincode(
                    order.getPincode()
            );

            List<OrderItem> orderItems =
                    orderItemRepository.findByOrderId(
                            order.getId()
                    );

            List<OrderItemDTO> itemDtos =
                    new ArrayList<>();

            for (OrderItem item : orderItems) {

                OrderItemDTO itemDto =
                        new OrderItemDTO();

                itemDto.setProductName(
                        item.getProductName()
                );

                itemDto.setQuantity(
                        item.getQuantity()
                );

                itemDto.setPrice(
                        item.getPrice()
                );

                itemDtos.add(itemDto);
            }

            dto.setItems(itemDtos);

            response.add(dto);
        }

        return response;
    }

    public Order cancelOrder(
            int orderId
    ) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Order not found"
                                )
                        );

        if (
                order.getStatus()
                        .equals("DELIVERED")
        ) {

            throw new RuntimeException(
                    "Delivered order cannot be cancelled"
            );
        }

        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    public Order updateOrderStatus(
            int orderId,
            String status
    ) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Order not found"
                                )
                        );

        order.setStatus(status);

        return orderRepository.save(order);
    }

    public DashboardResponseDTO getDashboardData() {

        DashboardResponseDTO dto =
                new DashboardResponseDTO();

        dto.setTotalProducts(
                productRepository.count()
        );

        dto.setTotalOrders(
                orderRepository.count()
        );

        dto.setTotalUsers(
                userRepository.count()
        );

        dto.setTotalRevenue(
                orderRepository.getTotalRevenue()
        );

        dto.setLowStockProducts(
                productRepository.countByQuantityBetween(
                        1,
                        10
                )
        );

        dto.setOutOfStockProducts(
                productRepository.countByQuantity(
                        0
                )
        );

        return dto;
    }

    public List<OrderStatusCountDTO>
    getOrderStatusCounts() {

        return orderRepository
				.getOrderStatusCounts();
	}
}