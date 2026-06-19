package com.ganesh.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.model.Product;
import com.ganesh.ecommerce.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 🔥 ADMIN ONLY (filter handles security)
    @PostMapping("/admin/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        Product saved = productService.addProduct(product);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        String msg = productService.deleteProduct(id);
        return ResponseEntity.ok(Map.of("message", msg));
    }

    // ✅ PUBLIC
    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(
            @PathVariable int id) {

        Product product =
                productService.getProductById(id);

        return ResponseEntity.ok(product);
    }
}