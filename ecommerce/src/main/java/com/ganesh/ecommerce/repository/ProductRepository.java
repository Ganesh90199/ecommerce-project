package com.ganesh.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ganesh.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}