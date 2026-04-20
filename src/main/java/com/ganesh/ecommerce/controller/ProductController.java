package com.ganesh.ecommerce.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @PostMapping("/admin/add")
    public String addProduct() {
        return "Product Added";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        return "Deleted product " + id;
    }

    @GetMapping("/all")
    public String getAll() {
        return "All products";
    }
}