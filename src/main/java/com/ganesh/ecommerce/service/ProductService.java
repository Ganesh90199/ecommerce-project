package com.ganesh.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.model.Product;
import com.ganesh.ecommerce.repository.ProductRepository;
import com.ganesh.ecommerce.exception.ResourceNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // ✅ Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ✅ Add product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // ✅ Get by ID
    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    // 🔥 Update product
    public Product updateProduct(int id, Product updatedProduct) {
        Product existing = getProductById(id);

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setDescription(updatedProduct.getDescription());
        existing.setCategory(updatedProduct.getCategory());
        existing.setQuantity(updatedProduct.getQuantity());
        existing.setImageUrl(updatedProduct.getImageUrl());

        return productRepository.save(existing);
    }

    // 🔥 Delete product
    public String deleteProduct(int id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        return "Product deleted successfully";
    }
}