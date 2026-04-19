package com.ganesh.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.config.JwtUtil;
import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 🔐 Register User
    public User register(User user) {

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // 🔐 Login User
    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            // 🔥 Return JWT token instead of message
            return jwtUtil.generateToken(email);
        }

        return "Invalid credentials";
    }
}