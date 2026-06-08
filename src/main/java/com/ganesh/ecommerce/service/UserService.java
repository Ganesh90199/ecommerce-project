package com.ganesh.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.repository.UserRepository;
import com.ganesh.ecommerce.config.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ REGISTER
    public String register(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists";
        }

        user.setRole("USER"); // default role
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "User Registered";
    }

    // ✅ CREATE ADMIN
    public String createAdmin(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists";
        }

        user.setRole("ADMIN");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "Admin Created";
    }

    // ✅ LOGIN (returns User, NOT String)
    public User loginUser(User request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) return null;

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return null;
        }

        return user;
    }

    // ✅ GENERATE TOKEN
    public String generateToken(String email, String role) {
        return jwtUtil.generateToken(email, role);
    }
}