package com.ganesh.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.model.Product;
import com.ganesh.ecommerce.repository.ProductRepository;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // GET all products
    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // POST new product
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
}