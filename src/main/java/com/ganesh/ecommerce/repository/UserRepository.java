package com.ganesh.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ganesh.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}