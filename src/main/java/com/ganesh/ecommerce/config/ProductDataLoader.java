package com.ganesh.ecommerce.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganesh.ecommerce.model.Product;
import com.ganesh.ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run(String... args) {

        try {

            // Prevent duplicate imports
            if (productRepository.count() > 0) {
                System.out.println("Products already exist. Skipping import.");
                return;
            }

            ClassPathResource resource = new ClassPathResource("products.json");

            if (!resource.exists()) {
                System.out.println("products.json not found.");
                return;
            }

            InputStream inputStream = resource.getInputStream();

            List<Product> products = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Product>>() {
                    }
            );

            productRepository.saveAll(products);

            System.out.println("====================================");
            System.out.println("Products Imported Successfully");
            System.out.println("Total Products Imported: " + products.size());
            System.out.println("====================================");

        } catch (Exception e) {

            System.out.println("====================================");
            System.out.println("Error Importing Products");
            System.out.println(e.getMessage());
            System.out.println("====================================");

            e.printStackTrace();
        }
    }
}