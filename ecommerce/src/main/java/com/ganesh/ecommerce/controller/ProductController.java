package com.ganesh.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.model.Product;
import com.ganesh.ecommerce.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }
}