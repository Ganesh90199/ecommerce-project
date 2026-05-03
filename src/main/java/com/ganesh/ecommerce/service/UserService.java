package com.ganesh.ecommerce.service;

import java.util.List;

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

    // ✅ REGISTER (Create or Update Password if email exists)
    public String register(User user) {

        List<User> existingUsers = userRepository.findByEmail(user.getEmail());

        if (!existingUsers.isEmpty()) {
            User existing = existingUsers.get(0);
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(existing);
            return "Password Updated";
        }

        // ✅ Always USER
        user.setRole("USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User Registered";
    }
    
    //ADMIN CREATION
    public String createAdmin(User user) {

        List<User> existingUsers = userRepository.findByEmail(user.getEmail());

        if (!existingUsers.isEmpty()) {
            return "User already exists";
        }

        user.setRole("ADMIN");  // ✅ Only here ADMIN is assigned
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "Admin Created";
    }

    // ✅ LOGIN (Validate password + Generate JWT token)
    public String login(User user) {

        List<User> users = userRepository.findByEmail(user.getEmail());

        if (users.isEmpty()) {
            return "User not found";
        }

        User u = users.get(0);

        // 🔐 Compare encrypted password
        if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {

            // ✅ Generate JWT with email + role
            return jwtUtil.generateToken(u.getEmail(), u.getRole());
        }

        return "Invalid password";
    }
}