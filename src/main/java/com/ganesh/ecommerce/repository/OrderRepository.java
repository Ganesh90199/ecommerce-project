package com.ganesh.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ganesh.ecommerce.dto.OrderStatusCountDTO;
import com.ganesh.ecommerce.model.Order;

public interface OrderRepository
        extends JpaRepository<Order, Integer> {

	List<Order> findByUserIdOrderByIdDesc(int userId);
    @Query(
        "SELECT COALESCE(SUM(o.totalAmount),0) " +
        "FROM Order o " +
        "WHERE o.status <> 'CANCELLED'"
    )
    double getTotalRevenue();
    @Query(
    	    "SELECT new com.ganesh.ecommerce.dto.OrderStatusCountDTO(" +
    	    "o.status, COUNT(o)) " +
    	    "FROM Order o " +
    	    "GROUP BY o.status"
    	)
    	List<OrderStatusCountDTO> getOrderStatusCounts();
}
