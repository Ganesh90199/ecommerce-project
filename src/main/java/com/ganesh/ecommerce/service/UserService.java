package com.ganesh.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.config.JwtUtil;
import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ REGISTER (update if exists)
    public String register(User user) {

        List<User> users = userRepository.findByEmail(user.getEmail());

        if (!users.isEmpty()) {
            User existing = users.get(0);
            existing.setPassword(user.getPassword());
            existing.setRole(user.getRole());
            userRepository.save(existing);
            return "User updated";
        }

        userRepository.save(user);
        return "User registered";
    }

    // ✅ LOGIN
    public String login(User user) {

        List<User> users = userRepository.findByEmail(user.getEmail());

        if (users.isEmpty()) {
            return "User not found";
        }

        for (User u : users) {
            if (u.getPassword().equals(user.getPassword())) {
                return jwtUtil.generateToken(u.getEmail(), u.getRole());
            }
        }

        return "Invalid password";
    }
}