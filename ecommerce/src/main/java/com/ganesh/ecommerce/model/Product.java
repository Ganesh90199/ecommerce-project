package com.ganesh.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}